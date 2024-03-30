package org.example.todolist.controller.response;

public record AuthResponse(
        String message,
        String token
) {}
