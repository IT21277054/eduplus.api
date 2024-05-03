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
            Optional<String> optionalUserRole = userRepository.findRoleByEmail(email);
            String roleString = optionalUserRole.map(userRole -> {
                try {
                    JSONObject jsonObject = new JSONObject(userRole);
                    return jsonObject.getString("role");
                } catch (JSONException e) {
                    e.printStackTrace();
                    return null;
                }
            }).orElse(null);
            return authService.generateToken(email,roleString);
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
