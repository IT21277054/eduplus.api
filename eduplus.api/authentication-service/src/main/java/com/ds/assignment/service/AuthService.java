package com.ds.assignment.service;

import com.ds.assignment.model.User;
import com.ds.assignment.model.UserRole;
import com.ds.assignment.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;

    @Autowired
    private RestTemplate restTemplate;

    public String saveUser(User user) {

        System.out.println("here in auth service for register");
        String url = "http://localhost:8081/api/notification/validateEmail";

        Optional<User> existUser = userRepository.findByEmail(user.getEmail());

        if(existUser.isPresent()){
            throw new Error("User Exist for "+user.getEmail());
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Map<String, String> requestBody = Map.of("email", user.getEmail(), "emailToken", user.getEmailToken());
        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

        System.out.println("here in after http");
        // Make the POST request to validate email
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, requestEntity, String.class);
        System.out.println("here in POST request to validate email");
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            return "User added Successfully";
        } else {
            throw new Error("User registration failed");
        }
    }

    public String generateToken(String email, String role) {
        return jwtService.generateToken(email,role);
    }

    public void validateToken(String token) {
        jwtService.validateToken(token);
    }

}
