package com.nexstudio.expensetracker_be.util;

import com.nexstudio.expensetracker_be.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AdminSeeder implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${extracker.admin.username}")
    private String adminUsername;
    @Value("${extracker.admin.username}")
    private String adminPassword;

    // TODO: Create Admin User
    @Override
    public void run(String... args) throws Exception {


        log.info("Admin user {} with password {} has been created", adminUsername, adminPassword);
    }

}
