package com.haduc.quicklibbooksmanagement.controller;

import com.haduc.quicklibbooksmanagement.dto.AuthOutput;
import com.haduc.quicklibbooksmanagement.dto.AuthenticationRequest;
import com.haduc.quicklibbooksmanagement.dto.AuthenticationResponse;
import com.haduc.quicklibbooksmanagement.entity.User;
import com.haduc.quicklibbooksmanagement.repository.UserRepository;
import com.haduc.quicklibbooksmanagement.service.AuthenticationService;
import com.haduc.quicklibbooksmanagement.dto.RegisterRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService service;

    private final UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthOutput> authenticate(@RequestBody AuthenticationRequest request) {
        AuthenticationResponse response = service.authenticate(request);
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        return ResponseEntity.ok(
                AuthOutput.builder()
                        .authenticationResponse(response)
                        .userId(user.getId())
                        .username(user.getUsername())
                        .email(user.getEmail())
                        .role(user.getRole().name())
                        .build()
        );
    }

    @PostMapping("/refresh-token")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        service.refreshToken(request, response);
    }


}
