package ru.fridrock.jir_backend.dto.tasks;

import ru.fridrock.jir_backend.models.enums.TaskPriority;
import ru.fridrock.jir_backend.models.enums.TaskSource;
import ru.fridrock.jir_backend.models.enums.TaskStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record TaskDto(
    UUID taskId,
    UUID projectId,
    String title,
    String description,
    LocalDateTime issued,
    LocalDateTime deadline,
    TaskPriority priority,
    Integer hoursSpent,
    TaskStatus status,
    TaskSource source) {
}
