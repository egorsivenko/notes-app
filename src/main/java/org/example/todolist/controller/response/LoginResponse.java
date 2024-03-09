package org.example.todolist.controller.response;

public record LoginResponse(
        String message,
        String token
) {}
