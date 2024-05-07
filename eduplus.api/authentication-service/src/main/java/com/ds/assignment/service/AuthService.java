package com.ds.assignment.service;

import com.ds.assignment.exception.UserExistsException;
import com.ds.assignment.model.User;
import com.ds.assignment.model.UserProfile;
import com.ds.assignment.model.UserRole;
import com.ds.assignment.repository.UserProfileRepository;
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
    UserProfileRepository userProfileRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;

    @Autowired
    private RestTemplate restTemplate;

    public String saveUser(User user) {

        String url = "http://localhost:8085/api/notification/validateEmail";

        Optional<User> existUser = userRepository.findByEmail(user.getEmail());
        System.out.println("here in auth service for register");
        if(existUser.isPresent()){
            throw new UserExistsException("User Exist for "+user.getEmail());
        }
        System.out.println("here after existuser");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Map<String, String> requestBody = Map.of("email", user.getEmail(), "emailToken", user.getEmailToken());
        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

        System.out.println("here in after http");
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, requestEntity, String.class);
        System.out.println("here in POST request to validate email");
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            User savedUser =userRepository.save(user);
            if(user.getRole().equals(UserRole.LEARNER)){
                UserProfile userProfile = new UserProfile();
                userProfile.setAccount_id(savedUser.getId());
                userProfile.setName(user.getName());
                userProfile.setEmail(user.getEmail());
                userProfile.setPhoneNumber((user.getPhoneNumber()));

                userProfileRepository.save(userProfile);
            }
            return "User added Successfully";
        } else {
            throw new RuntimeException("User registration failed");
        }
    }

    public String generateToken(String id,String email, String role) {
        return jwtService.generateToken(id,email,role);
    }

    public void validateToken(String token) {
        jwtService.validateToken(token);
    }

}
