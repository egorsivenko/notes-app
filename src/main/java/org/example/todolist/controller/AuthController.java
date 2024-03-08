package org.example.todolist.controller;

import jakarta.validation.Valid;
import org.example.todolist.controller.dto.UserDto;
import org.example.todolist.controller.response.LoginResponse;
import org.example.todolist.security.jwt.JwtProvider;
import org.example.todolist.service.RegistrationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
public class AuthController {

    private static final String LOGIN_MESSAGE = "User logged in successfully";
    private static final String REGISTER_MESSAGE = "User registered successfully";

    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final RegistrationService registrationService;

    public AuthController(AuthenticationManager authenticationManager,
                          JwtProvider jwtProvider,
                          RegistrationService registrationService) {
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
        this.registrationService = registrationService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody UserDto userDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userDto.getUsername(), userDto.getPassword()));

        String token = jwtProvider.generateToken(authentication);
        return ResponseEntity.ok(new LoginResponse(LOGIN_MESSAGE, token));
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody UserDto userDto) {
        registrationService.registerUser(userDto);
        return ResponseEntity.ok(REGISTER_MESSAGE);
    }
}
