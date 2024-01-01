package com.haduc.quicklibbooksmanagement.dto;

import com.haduc.quicklibbooksmanagement.entity.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String username;
    private String email;
    private String password;
    private UserRole role;
    private Long libraryId;
}
