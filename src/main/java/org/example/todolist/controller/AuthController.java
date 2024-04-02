package org.example.todolist.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.example.todolist.controller.dto.UserDto;
import org.example.todolist.exception.UsernameTakenException;
import org.example.todolist.service.AuthService;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("api/V1/auth")
public class AuthController {

    public static final int COOKIE_MAX_AGE = 30 * 60;
    public static final String LOGIN_ERROR = "Invalid username or password";

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("userDto", new UserDto());
        return "auth/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute UserDto userDto, HttpServletResponse response, Model model) {
        String token;
        try {
            token = authService.loginUser(userDto);
        } catch (AuthenticationException e) {
            model.addAttribute("error", LOGIN_ERROR);
            return "auth/login";
        }
        response.addCookie(buildAuthCookie(token));
        return "redirect:/api/V1/notes/list";
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("userDto", new UserDto());
        return "auth/register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute UserDto userDto, HttpServletResponse response, Model model) {
        String token;
        try {
            token = authService.registerUser(userDto);
        } catch (UsernameTakenException e) {
            model.addAttribute("error", e.getMessage());
            return "auth/register";
        }
        response.addCookie(buildAuthCookie(token));
        return "redirect:/api/V1/notes/list";
    }

    private Cookie buildAuthCookie(String token) {
        Cookie cookie = new Cookie("Token", token);
        cookie.setMaxAge(COOKIE_MAX_AGE);
        cookie.setPath("/");
        return cookie;
    }
}
