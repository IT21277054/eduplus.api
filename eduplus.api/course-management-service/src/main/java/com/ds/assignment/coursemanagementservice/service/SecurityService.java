package com.ds.assignment.coursemanagementservice.service;

import org.springframework.stereotype.Service;

@Service
public class SecurityService {

    public boolean hasRole(String requiredRole) {
        System.out.println("here in security hasrole");
        return requiredRole.equals("ADMIN");
    }
}