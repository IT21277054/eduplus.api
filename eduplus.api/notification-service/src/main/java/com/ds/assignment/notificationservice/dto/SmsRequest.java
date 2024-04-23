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
public class SmsRequest {
    @NotNull
    @NotNull
    @Pattern(regexp = "\\+\\d{1,3} \\d{9,11}", message = "Invalid phone number format. Should be in the format: '+[country code] [phone number]'")
    private String phoneNumber;
    @NotNull
    @NotEmpty
    @Size(min = 1, message = "Subject must be between 1 and 100 characters")
    private String message;
}
