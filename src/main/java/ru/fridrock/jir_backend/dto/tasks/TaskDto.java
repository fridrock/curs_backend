package ru.fridrock.jir_backend.dto.tasks;

import lombok.Builder;
import org.springframework.format.annotation.DateTimeFormat;
import ru.fridrock.jir_backend.models.enums.TaskPriority;
import ru.fridrock.jir_backend.models.enums.TaskSource;
import ru.fridrock.jir_backend.models.enums.TaskStatus;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record TaskDto(
        UUID taskId,
        UUID projectId,
        String title,
        String description,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        LocalDateTime deadline,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        LocalDateTime issued,
        TaskSource source,
        TaskStatus status,
        TaskPriority priority,
        Integer hoursSpent) {
}
