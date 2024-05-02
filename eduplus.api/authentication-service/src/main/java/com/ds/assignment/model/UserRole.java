package com.ds.assignment.model;

import java.util.HashMap;
import java.util.Map;

public enum UserRole {
    ADMIN,
    INSTRUCTOR,
    LEARNER;

    // Map to store string representation to enum mapping
    private static final Map<String, UserRole> map = new HashMap<>();
    static {
        for (UserRole role : UserRole.values()) {
            map.put(role.toString(), role);
        }
    }

    // Method to get enum value from string representation
    public static UserRole fromString(String value) {
        return map.get(value);
    }
}
