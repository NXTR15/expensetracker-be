package com.nexstudio.expensetracker_be.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthenticationRequest {
    @NotBlank
    private String usernameOrEmail;
    @NotBlank
    private String password;
}
