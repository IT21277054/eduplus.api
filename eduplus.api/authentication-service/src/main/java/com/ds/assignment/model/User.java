package com.ds.assignment.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    @NotEmpty
    private String name;
    @NotNull
    @NotEmpty
    @Email(message = "Invalid email address")
    @Pattern(regexp = ".+@.+\\..+",message ="Invalid email address" )
    private String email;
    @NotNull
    @NotEmpty
    @Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
    private String password;

}
