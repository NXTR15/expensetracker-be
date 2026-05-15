package com.nexstudio.expensetracker_be.util;

import com.nexstudio.expensetracker_be.dto.request.AuthenticationRequest;
import com.nexstudio.expensetracker_be.dto.request.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ValidationUtils {
    public void validateLoginRequest(AuthenticationRequest request) {
        validateUsername(request.getUsernameOrEmail());
        validatePassword(request.getPassword());
    }

    public void validateRegisterRequest(RegisterRequest request) {
        validateUsername(request.getUsername());
        validatePassword(request.getPassword());
    }

    private void validateUsername(String username) {
        if (!username.matches(StaticUtils.REGEX)) {
            throw new IllegalArgumentException("Username only allows alphanumeric, underscore, and exclamation mark");
        }

        if (username.length() > StaticUtils.DEFAULT_LENGTH.get("username")) {
            throw new IllegalArgumentException("Username length must not exceed " + StaticUtils.DEFAULT_LENGTH.get("username"));
        }
    }

    private void validatePassword(String password) {
        if (!password.matches(StaticUtils.REGEX)) {
            throw new IllegalArgumentException("Password only allows alphanumeric, underscore, and exclamation mark");
        }

        if (password.length() > StaticUtils.DEFAULT_LENGTH.get("password")) {
            throw new IllegalArgumentException("Password length must not exceed " + StaticUtils.DEFAULT_LENGTH.get("password"));
        }
    }
}
