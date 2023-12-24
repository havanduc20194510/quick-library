package com.haduc.quicklibbooksmanagement.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthOutput {
    private Long userId;
    private String username;
    private String email;
    private String role;
    private AuthenticationResponse authenticationResponse;
}
