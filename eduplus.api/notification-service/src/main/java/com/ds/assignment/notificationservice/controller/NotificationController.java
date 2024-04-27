package com.ds.assignment.notificationservice.controller;

import com.ds.assignment.notificationservice.dto.EmailRequest;
import com.ds.assignment.notificationservice.dto.SmsRequest;
import com.ds.assignment.notificationservice.service.NotificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.mail.MailException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notification")
@RequiredArgsConstructor
@Validated
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping("/sms")
    @ResponseStatus(HttpStatus.CREATED)
    public String sendSms(@Valid @RequestBody SmsRequest smsRequest){
        return notificationService.sendSms(smsRequest);
    }

    @PostMapping("/send-email")
    public String sendEmail(@Valid @RequestBody EmailRequest emailRequest) {
        try {
            notificationService.sendEmail(emailRequest);
            return "Email sent successfully";
        } catch (MailException e) {
            return "Failed to send email: " + e.getMessage();
        }
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleValidationExceptions(MethodArgumentNotValidException ex) {
        return ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
    }
}
