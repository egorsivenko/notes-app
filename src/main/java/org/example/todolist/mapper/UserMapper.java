package org.example.todolist.mapper;

import org.example.todolist.controller.response.UserResponse;
import org.example.todolist.entity.Role;
import org.example.todolist.entity.User;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserMapper {

    public UserResponse toUserResponse(User user) {
        return new UserResponse(
                user.getUsername(),
                user.getRoles()
                        .stream()
                        .map(Role::getName)
                        .collect(Collectors.toSet())
        );
    }
}
