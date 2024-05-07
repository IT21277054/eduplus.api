package com.ds.assignment.config;

import com.ds.assignment.model.User;
import com.ds.assignment.model.UserRole;
import com.ds.assignment.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class AdminInitializer implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if (!userRepository.existsByRole("ADMIN")) {
            User admin = new User();
            admin.setName("Admin");
            admin.setPassword(passwordEncoder.encode("admin123")); // Don't forget to hash the password
            admin.setEmail("admin@edpuplus.com");
            admin.setRole(UserRole.ADMIN);

            userRepository.save(admin);
        }

    }
}
