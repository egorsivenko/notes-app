package org.example.todolist.controller;

import jakarta.validation.Valid;
import org.example.todolist.controller.dto.UserDto;
import org.example.todolist.controller.response.LoginResponse;
import org.example.todolist.controller.response.RegistrationResponse;
import org.example.todolist.service.auth.LoginService;
import org.example.todolist.service.auth.RegistrationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
public class AuthController {

    private static final String LOGIN_MESSAGE = "User logged in successfully";
    private static final String REGISTRATION_MESSAGE = "User registered successfully";

    private final LoginService loginService;
    private final RegistrationService registrationService;

    public AuthController(LoginService loginService, RegistrationService registrationService) {
        this.loginService = loginService;
        this.registrationService = registrationService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody UserDto userDto) {
        String token = loginService.loginUser(userDto);
        return ResponseEntity.ok(new LoginResponse(LOGIN_MESSAGE, token));
    }

    @PostMapping("/register")
    public ResponseEntity<RegistrationResponse> register(@Valid @RequestBody UserDto userDto) {
        Long userId = registrationService.registerUser(userDto);
        return ResponseEntity.ok(new RegistrationResponse(REGISTRATION_MESSAGE, userId));
    }
}
