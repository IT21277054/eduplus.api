package com.ds.assignment.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {

    @NotNull
    @NotEmpty
    private String email;
    @NotNull
    @NotEmpty
    @Email(message = "Invalid email address")
    @Pattern(regexp = ".+@.+\\..+",message ="Invalid email address" )
    private String password;
}
