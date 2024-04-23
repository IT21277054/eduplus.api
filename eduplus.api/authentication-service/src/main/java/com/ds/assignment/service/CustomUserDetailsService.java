package com.ds.assignment.service;

import com.ds.assignment.config.CustomUserDetails;
import com.ds.assignment.model.User;
import com.ds.assignment.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> existUser = userRepository.findByName(username);
        return existUser.map(CustomUserDetails::new).orElseThrow(() -> new UsernameNotFoundException("User not found for the : " + username));
    }
}
