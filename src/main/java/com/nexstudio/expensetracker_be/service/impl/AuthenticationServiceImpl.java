package com.nexstudio.expensetracker_be.service.impl;

import com.nexstudio.expensetracker_be.dto.request.AuthenticationRequest;
import com.nexstudio.expensetracker_be.dto.response.AuthenticationResponse;
import com.nexstudio.expensetracker_be.entity.UserEntity;
import com.nexstudio.expensetracker_be.service.AuthenticationService;
import com.nexstudio.expensetracker_be.util.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    @Override
    public AuthenticationResponse login(AuthenticationRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsernameOrEmail(),
                        request.getPassword()
                )
        );

        log.info("User logged in with username or email {}", request.getUsernameOrEmail());

        UserEntity user = (UserEntity) authentication.getPrincipal();
        String token = jwtProvider.generateToken(user);

        return AuthenticationResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .role(user.getRole().getDisplayName())
                .token(token)
                .build();
    }
}
