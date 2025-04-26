package ru.fridrock.jir_backend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.stereotype.Service;
import ru.fridrock.jir_backend.dto.tasks.AiCreateTaskDto;
import ru.fridrock.jir_backend.dto.tasks.AiTaskDto;
import ru.fridrock.jir_backend.dto.tasks.TaskDto;
import ru.fridrock.jir_backend.exception.AiGenerationException;
import ru.fridrock.jir_backend.exception.NotFoundException;
import ru.fridrock.jir_backend.mappers.IMapper;
import ru.fridrock.jir_backend.models.TaskEntity;
import ru.fridrock.jir_backend.models.enums.TaskPriority;
import ru.fridrock.jir_backend.models.enums.TaskSource;
import ru.fridrock.jir_backend.models.enums.TaskStatus;
import ru.fridrock.jir_backend.repository.ProjectRepository;
import ru.fridrock.jir_backend.repository.TaskRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskService {
    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final IMapper<TaskDto, TaskEntity> mapper;
    private static final String prompt = """
                Here is input for tasks: %s
                Current Date is %s.  
                Parse date offset from inputText
                Determine deadline using formula: current date + offset
                Determine title - name for task from inputText, don't provide information about date, or priority
                Determine description for task from inputText, if there is no data for description, just write empty string
                Determine priority of task from inputText
                Priority can be strictly only one of three values: LOW, HIGH, CRITICAL
                create json with deadline, title, description, priority
        """;
    private final OllamaChatModel ollamaChatModel;
    private final ObjectMapper objectMapper;

//                If there is a lot of data, you can subtract it on subparagraphs and format each on new line

    public TaskDto create(TaskDto dto) {
        var project = projectRepository.findById(dto.projectId())
            .orElseThrow(() -> projectNotFound(dto.projectId()));
        var task = TaskEntity.builder()
            .project(project)
            .title(dto.title())
            .description(dto.description())
            .issued(LocalDateTime.now())
            .deadline(dto.deadline())
            .hoursSpent(0)
            .priority(dto.priority())
            .status(TaskStatus.TODO)
            .source(ObjectUtils.isNotEmpty(dto.source()) ? dto.source() : TaskSource.PERSON)
            .build();
        project.getTasks()
            .add(task);
        projectRepository.save(project);
        return mapper.mapToDto(taskRepository.save(task));
    }

    public TaskDto update(TaskDto dto) {
        var taskEntity = taskRepository.findById(dto.taskId())
            .orElseThrow(() -> taskNotFound(dto.taskId()));
        taskEntity = taskEntity.toBuilder()
            .description(dto.description())
            .title(dto.title())
            .deadline(dto.deadline())
            .priority(dto.priority())
            .hoursSpent(dto.hoursSpent())
            .status(dto.status())
            .build();
        return mapper.mapToDto(taskRepository.save(taskEntity));
    }

    public void delete(UUID taskId) {
        var taskEntity = taskRepository.findById(taskId)
            .orElseThrow(() -> taskNotFound(taskId));
        taskRepository.delete(taskEntity);
    }

    public TaskDto getById(UUID taskId) {
        var task = taskRepository.findById(taskId)
            .orElseThrow(() -> taskNotFound(taskId));
        return mapper.mapToDto(task);
    }

    public List<TaskDto> getByProjectId(UUID projectId) {
        List<TaskEntity> tasks = taskRepository.findByProjectId(projectId);
        return tasks.stream()
            .map(mapper::mapToDto)
            .collect(Collectors.toList());
    }

    private NotFoundException projectNotFound(UUID projectId) {
        return new NotFoundException("project with id: " +
            projectId +
            " wasn't found");
    }

    private NotFoundException taskNotFound(UUID taskId) {
        return new NotFoundException("task with id: " +
            taskId +
            " wasn't found");
    }

    public TaskDto generateTasksWithAi(AiCreateTaskDto dto) {
        String promptText = String.format(prompt, dto.message(), LocalDateTime.now());
        ChatResponse response = ollamaChatModel.call(new Prompt(promptText));
        TypeReference<List<AiTaskDto>> jacksonTypeReference = new TypeReference<List<AiTaskDto>>() {
        };

        String output = response.getResult()
            .getOutput()
            .getText()
            .replaceAll("```json|```", "")
            .trim();
        ;
        try {
            AiTaskDto aiTask = objectMapper.readValue(output, AiTaskDto.class);
            log.info("received ai task: {}", aiTask);
            TaskDto taskToCreate = taskDtoFromAiTask(aiTask, dto.projectId());
            log.info("mapped to task: {}", taskToCreate);
            TaskDto created = create(taskToCreate);
            log.info("created task: {}", created);
            return created;
        } catch (JsonProcessingException ex) {
            throw new AiGenerationException("Failed to parse AI response");
        }
    }

    private TaskDto taskDtoFromAiTask(AiTaskDto aiTask, UUID projectId) {
        return TaskDto.builder()
            .projectId(projectId)
            .source(TaskSource.AI)
            .deadline(parseDeadline(aiTask.deadline()))
            .description(aiTask.description())
            .title(aiTask.title())
            .priority(TaskPriority.valueOf(aiTask.priority()))
            .build();
    }

    private LocalDateTime parseDeadline(String deadline) {
        return LocalDateTime.parse(deadline);
    }
}
