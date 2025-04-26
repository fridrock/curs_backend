package ru.fridrock.jir_backend.dto.tasks;

import java.util.UUID;

public record AiCreateTaskDto(UUID projectId,
                              String message) {
}
