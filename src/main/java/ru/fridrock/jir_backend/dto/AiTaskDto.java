package ru.fridrock.jir_backend.dto;

public record AiTaskDto(String title,
                        String nextDate,
                        String description,
                        String priority) {
}
