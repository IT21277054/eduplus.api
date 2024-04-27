package com.ds.assignment.notificationservice.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmailRequest {

    @NotNull
    @NotEmpty
    @Email(message = "Invalid email address")
    @Pattern(regexp = ".+@.+\\..+",message ="Invalid email address" )
    private String to;
    @NotNull
    @NotEmpty
    @Size(min = 1, max = 100, message = "Subject must be between 1 and 100 characters")
    private String subject;
    @NotNull
    @NotEmpty
    private String body;
}
