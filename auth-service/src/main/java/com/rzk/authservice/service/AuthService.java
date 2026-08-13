package com.rzk.authservice.service;

import com.rzk.authservice.dto.RegisterRequestDto;
import com.rzk.authservice.dto.UserResponseDto;
import com.rzk.authservice.model.Role;
import com.rzk.authservice.model.User;
import com.rzk.authservice.model.UserRole;
import com.rzk.authservice.model.UserRoleId;
import com.rzk.authservice.repository.RoleRepository;
import com.rzk.authservice.repository.UserRepository;
import com.rzk.authservice.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.rzk.authservice.dto.LoginRequestDto;
import com.rzk.authservice.dto.LoginResponseDto;
import com.rzk.authservice.model.UserRole;
import java.util.List;
import java.util.stream.Collectors;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UserResponseDto register(RegisterRequestDto request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("Username already taken");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already in use");
        }

        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new IllegalStateException("Default role ROLE_USER not found"));

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setCreatedAt(Instant.now());

        User savedUser = userRepository.save(user);

        UserRoleId userRoleId = new UserRoleId();
        userRoleId.setUserId(savedUser.getId());
        userRoleId.setRoleId(userRole.getId());

        UserRole userRoleEntry = new UserRole();
        userRoleEntry.setId(userRoleId);
        userRoleEntry.setUser(savedUser);
        userRoleEntry.setRole(userRole);

        userRoleRepository.save(userRoleEntry);

        return new UserResponseDto(savedUser.getId(), savedUser.getUsername(), savedUser.getEmail());
    }

    public LoginResponseDto login(LoginRequestDto request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Invalid username or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid username or password");
        }

        List<UserRole> userRoles = userRoleRepository.findByUser_Id(user.getId());
        List<String> roleNames = userRoles.stream()
                .map(ur -> ur.getRole().getName())
                .collect(Collectors.toList());

        String token = jwtService.generateToken(user.getUsername(), roleNames);
        return new LoginResponseDto(token);
    }
}