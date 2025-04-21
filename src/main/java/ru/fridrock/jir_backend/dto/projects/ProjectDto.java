package ru.fridrock.jir_backend.dto.projects;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record ProjectDto(
    @NotBlank
    String name,
    UUID projectId) {
}
