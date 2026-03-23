package com.hospital.admin.config;

import com.hospital.admin.entity.User;
import com.hospital.admin.enums.Role;
import com.hospital.admin.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        createDefaultAdminIfNotExists();
    }

    private void createDefaultAdminIfNotExists() {
        String adminEmail = "admin@hospital.com";
        if (!userRepository.existsByEmail(adminEmail)) {
            User admin = User.builder()
                    .firstName("System")
                    .lastName("Admin")
                    .email(adminEmail)
                    .password(passwordEncoder.encode("Admin@1234"))
                    .phone("0000000000")
                    .role(Role.ADMIN)
                    .build();
            admin.setIsActive(true);
            userRepository.save(admin);
            log.info("✅ Default admin user created: {} / Admin@1234", adminEmail);
        } else {
            log.info("ℹ️  Admin user already exists. Skipping seed.");
        }
    }
}
