package com.nexstudio.expensetracker_be.util;

import com.nexstudio.expensetracker_be.constants.Constants;
import com.nexstudio.expensetracker_be.entity.UserEntity;
import com.nexstudio.expensetracker_be.enums.UserRole;
import com.nexstudio.expensetracker_be.repository.main.UserRepository;
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
    @Value("${extracker.admin.password}")
    private String adminPassword;

    @Override
    public void run(String... args) throws Exception {
        UserEntity user = userRepository.getUser(adminUsername);

        if(user == null){
            userRepository.saveUser(
                    UserEntity.builder()
                            .username(adminUsername)
                            .password(passwordEncoder.encode(adminPassword))
                            .email(null)
                            .role(UserRole.ADMIN)
                            .inboundAddress(null)
                            .status(Constants.ACTIVE)
                            .build()
            );

            log.info("Created Admin User {} with password {}", adminUsername, adminPassword);
            return;
        }

        log.info("Admin user {} with password {} has already been created", adminUsername, adminPassword);
    }

}
