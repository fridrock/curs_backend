package ru.fridrock.jir_backend.dto.input.user;

public record RegisterRequest(String username, String password, String email) {
}
