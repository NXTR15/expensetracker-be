package com.nexstudio.expensetracker_be.controller;

import com.nexstudio.expensetracker_be.dto.request.AuthenticationRequest;
import com.nexstudio.expensetracker_be.dto.request.RegisterRequest;
import com.nexstudio.expensetracker_be.dto.response.AuthenticationResponse;
import com.nexstudio.expensetracker_be.dto.response.api.ApiResponse;
import com.nexstudio.expensetracker_be.service.AuthenticationService;
import com.nexstudio.expensetracker_be.service.UserService;
import com.nexstudio.expensetracker_be.util.ResponseUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthenticationResponse>> login(@Valid @RequestBody AuthenticationRequest request) {
        log.info("=== STARTING /login ENDPOINT ===");

        AuthenticationResponse response = authenticationService.login(request);
        return ResponseUtil.buildSingleResponse(HttpStatus.OK, "Login success", response);
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Object>> register(@Valid @RequestBody RegisterRequest request) {
        log.info("=== STARTING /register ENDPOINT ===");

        userService.register(request);
        return ResponseUtil.buildSingleResponse(HttpStatus.OK, "Register Success", null);
    }
}
