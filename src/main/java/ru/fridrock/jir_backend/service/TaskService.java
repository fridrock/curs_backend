package ru.fridrock.jir_backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.fridrock.jir_backend.dto.tasks.TaskDto;
import ru.fridrock.jir_backend.exception.NotFoundException;
import ru.fridrock.jir_backend.mappers.IMapper;
import ru.fridrock.jir_backend.models.TaskEntity;
import ru.fridrock.jir_backend.repository.ProjectRepository;
import ru.fridrock.jir_backend.repository.TaskRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final IMapper<TaskDto, TaskEntity> mapper;

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
}
