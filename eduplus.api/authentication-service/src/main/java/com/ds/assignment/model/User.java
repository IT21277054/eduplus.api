package com.ds.assignment.model;


import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;


@Document(collection = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @MongoId
    private String id;
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
    private UserRole role = UserRole.LEARNER;
    private String emailToken;

}
