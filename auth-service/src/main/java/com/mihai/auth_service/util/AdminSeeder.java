package com.mihai.auth_service.util;

import com.mihai.auth_service.dto.CreateUserRequest;
import com.mihai.auth_service.entity.Role;
import com.mihai.auth_service.exception.ConflictException;
import com.mihai.auth_service.repository.CredentialRepository;
import com.mihai.auth_service.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AdminSeeder implements ApplicationRunner {

    private final CredentialRepository credentialRepository;
    private final AuthService authService;

    @Value("${admin.username}") private String username;
    @Value("${admin.password}") private String password;
    @Value("${admin.email}")    private String email;

    @Override
    public void run(ApplicationArguments args) throws InterruptedException {
        if (credentialRepository.existsByRole(Role.ADMIN)) {
            log.info("Admin account already exists, skipping seeding");
            return;
        }

        for (int attempt = 1; attempt <= 10; attempt++) {
            try {
                authService.createUser(new CreateUserRequest(
                        username, password, "Administrator", email, null, Role.ADMIN));
                log.info("Default admin '{}' created", username);
                return;
            } catch (ConflictException e) {
                log.error("Cannot seed admin: {}", e.getMessage());
                return;
            } catch (RuntimeException e) {
                log.warn("User service not ready (attempt {}/10), retrying in 3s", attempt);
                Thread.sleep(3000);
            }
        }
        log.error("Could not create default admin: user-service unreachable");
    }
}
