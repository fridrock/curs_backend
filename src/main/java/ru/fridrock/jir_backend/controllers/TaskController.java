package ru.fridrock.jir_backend.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.fridrock.jir_backend.dto.tasks.TaskDto;
import ru.fridrock.jir_backend.service.TaskService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
@SecurityRequirement(name = "Authorization")
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public TaskDto create(@Valid TaskDto TaskDto) {
        return taskService.create(TaskDto);
    }

    @PatchMapping
    public TaskDto edit(@Valid TaskDto TaskDto) {
        return taskService.update(TaskDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        taskService.delete(id);
    }

    ;

    @GetMapping("/{projectId}")

    public List<TaskDto> get(@PathVariable UUID projectId) {
        return taskService.getByProjectId(projectId);
    }

    @GetMapping("/{id}")
    public TaskDto getById(@PathVariable("id") UUID id) {
        return taskService.getById(id);
    }

}
