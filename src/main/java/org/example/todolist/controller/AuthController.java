package org.example.todolist.controller;

import jakarta.validation.Valid;
import org.example.todolist.controller.dto.UserDto;
import org.example.todolist.entity.Role;
import org.example.todolist.entity.User;
import org.example.todolist.repository.RoleRepository;
import org.example.todolist.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("api/auth")
public class AuthController {

    private static final String LOGIN_RESPONSE = "User logged in successfully";
    private static final String REGISTER_RESPONSE = "User registered successfully";
    private static final String USERNAME_TAKEN_MESSAGE = "Username is already taken";
    private static final String USER_ROLE_NAME = "USER";

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public AuthController(AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder,
                          UserRepository userRepository, RoleRepository roleRepository) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody UserDto userDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userDto.getUsername(), userDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return ResponseEntity.ok(LOGIN_RESPONSE);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody UserDto userDto) {
        if (userRepository.existsByUsername(userDto.getUsername())) {
            return ResponseEntity.badRequest().body(USERNAME_TAKEN_MESSAGE);
        }
        User user = buildNewUser(userDto);
        userRepository.save(user);

        return ResponseEntity.ok(REGISTER_RESPONSE);
    }

    private User buildNewUser(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        Role role = roleRepository.findByName(USER_ROLE_NAME).orElseThrow();
        user.setRoles(Collections.singleton(role));
        return user;
    }
}
