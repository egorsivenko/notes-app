package org.example.todolist.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.example.todolist.controller.dto.UserDto;
import org.example.todolist.controller.response.AuthResponse;
import org.example.todolist.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Authentication")
@RequestMapping("api/V2/auth")
public class AuthRestController {

    private static final String LOGIN_MESSAGE = "User logged in successfully";
    private static final String REGISTRATION_MESSAGE = "User registered successfully";

    private final AuthService authService;

    public AuthRestController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(summary = "Login")
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody UserDto userDto) {
        String token = authService.loginUser(userDto);
        return ResponseEntity.ok(new AuthResponse(LOGIN_MESSAGE, token));
    }

    @Operation(summary = "Registration")
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody UserDto userDto) {
        String token = authService.registerUser(userDto);
        return ResponseEntity.ok(new AuthResponse(REGISTRATION_MESSAGE, token));
    }
}
