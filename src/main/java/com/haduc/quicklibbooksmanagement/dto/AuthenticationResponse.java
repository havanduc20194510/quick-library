package com.haduc.quicklibbooksmanagement.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    private Long userId;
    private String username;
    private String email;
    private String role;
    private String accessToken;
    private String refreshToken;
}
