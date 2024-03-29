package org.example.todolist.controller.response;

import java.util.Set;

public record UserResponse(
        String username,
        Set<String> roles
) {}
