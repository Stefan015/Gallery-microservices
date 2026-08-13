package com.rzk.authservice.controller;

import com.rzk.authservice.dto.LoginRequestDto;
import com.rzk.authservice.dto.LoginResponseDto;
import com.rzk.authservice.dto.RegisterRequestDto;
import com.rzk.authservice.dto.UserResponseDto;
import com.rzk.authservice.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> register(@Valid @RequestBody RegisterRequestDto request) {
        UserResponseDto response = authService.register(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto request) {
        LoginResponseDto response = authService.login(request);
        return ResponseEntity.ok(response);
    }
}