package com.nexstudio.expensetracker_be.service.impl;

import com.nexstudio.expensetracker_be.constants.Constants;
import com.nexstudio.expensetracker_be.dto.request.RegisterRequest;
import com.nexstudio.expensetracker_be.entity.UserEntity;
import com.nexstudio.expensetracker_be.enums.UserRole;
import com.nexstudio.expensetracker_be.repository.main.UserRepository;
import com.nexstudio.expensetracker_be.service.UserService;
import com.nexstudio.expensetracker_be.util.ValidationUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ValidationUtils validationUtils;

    @Override
    public void register(RegisterRequest request) {
        validationUtils.validateRegisterRequest(request);

        UserEntity user = UserEntity.builder()
                .username(request.getUsername().toLowerCase())
                .name(request.getName() != null ? request.getName() : null)
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail().toLowerCase())
                .role(UserRole.USER)
                .status(Constants.ACTIVE)
                .inboundAddress(null)
                .build();

        userRepository.saveUser(user);

        log.info("User successfully created with username {}", user.getUsername());
    }
}
