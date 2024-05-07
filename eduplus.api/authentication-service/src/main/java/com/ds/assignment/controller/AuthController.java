package com.ds.assignment.controller;

import com.ds.assignment.dto.AuthRequest;
import com.ds.assignment.model.User;
import com.ds.assignment.model.UserRole;
import com.ds.assignment.repository.UserRepository;
import com.ds.assignment.service.AuthService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @PostMapping("/register")
    public String createUser(@RequestBody User user){
        return authService.saveUser(user);
    }
    @PostMapping("/token")
    public String getToken(@RequestBody AuthRequest authRequest){
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(),authRequest.getPassword()));
        if(authenticate.isAuthenticated()){
            String email = authRequest.getEmail();
            Optional<String> optionalUser = userRepository.findRoleByEmail(email);
            System.out.println(optionalUser);
            if (optionalUser.isPresent()) {
                String userJson = optionalUser.get();
                try {
                    JSONObject jsonObject = new JSONObject(userJson);
                    String id = jsonObject.getString("_id");
                    String role = jsonObject.getString("role");
                    return authService.generateToken(id, email, role);
                } catch (JSONException e) {
                    e.printStackTrace();
                    throw new RuntimeException("Error parsing user data");
                }
            } else {
                throw new RuntimeException("User not found");
            }
        }else{
            throw new RuntimeException("Invalid access");
        }
    }

    @GetMapping("/validate")
    public String validateToken(@RequestParam("token") String token){
        authService.validateToken(token);
        return "Token is valid";
    }


}
