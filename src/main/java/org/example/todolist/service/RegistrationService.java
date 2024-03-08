package org.example.todolist.service;

import org.example.todolist.controller.dto.UserDto;
import org.example.todolist.entity.Role;
import org.example.todolist.entity.User;
import org.example.todolist.exception.UsernameTakenException;
import org.example.todolist.repository.RoleRepository;
import org.example.todolist.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class RegistrationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public RegistrationService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(UserDto userDto) {
        String username = userDto.getUsername();

        if (userRepository.existsByUsername(username)) {
            throw new UsernameTakenException(username);
        }
        User user = buildNewUser(userDto);
        userRepository.save(user);
    }

    private User buildNewUser(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        Role role = roleRepository.findByName("USER").orElseThrow();
        user.setRoles(Collections.singleton(role));
        return user;
    }
}
