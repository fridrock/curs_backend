package ru.fridrock.jir_backend.dto.tasks;

public record AiTaskDto(String title,
                        String deadline,
                        String description,
                        String priority) {
}
