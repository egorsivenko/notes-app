package org.example.todolist.controller.response;

import org.example.todolist.entity.RoleName;

import java.util.Set;

public record UserResponse(
        String username,
        Set<RoleName> roles
) {}
