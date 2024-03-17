package org.example.todolist.controller.response;

import java.util.UUID;

public record CategoryResponse (
        UUID id,
        String name
) {}
