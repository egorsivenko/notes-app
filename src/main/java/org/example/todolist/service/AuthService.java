package org.example.todolist.service;

import org.example.todolist.controller.dto.UserDto;
import org.example.todolist.entity.Role;
import org.example.todolist.entity.RoleName;
import org.example.todolist.entity.User;
import org.example.todolist.exception.UsernameTakenException;
import org.example.todolist.repository.RoleRepository;
import org.example.todolist.repository.UserRepository;
import org.example.todolist.security.jwt.JwtProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    public AuthService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager, JwtProvider jwtProvider) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
    }

    public String loginUser(UserDto userDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userDto.getUsername(), userDto.getPassword()));

        return jwtProvider.generateToken(authentication);
    }

    public String registerUser(UserDto userDto) {
        String username = userDto.getUsername();

        if (userRepository.existsByUsername(username)) {
            throw new UsernameTakenException(username);
        }
        User user = buildNewUser(userDto);
        userRepository.save(user);
        return loginUser(userDto);
    }

    private User buildNewUser(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        Role role = roleRepository.findByName(RoleName.USER.name()).orElseThrow();
        user.setRoles(Collections.singleton(role));
        return user;
    }
}
