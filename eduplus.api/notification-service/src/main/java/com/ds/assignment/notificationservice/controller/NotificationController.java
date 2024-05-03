package com.ds.assignment.notificationservice.controller;

import com.ds.assignment.notificationservice.dto.EmailRequest;
import com.ds.assignment.notificationservice.dto.EmailValidateRequest;
import com.ds.assignment.notificationservice.dto.OtpVerifyRequest;
import com.ds.assignment.notificationservice.dto.SmsRequest;
import com.ds.assignment.notificationservice.exception.NotificationException;
import com.ds.assignment.notificationservice.service.NotificationService;
import com.ds.assignment.notificationservice.service.impl.OtpService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private final OtpService otpService;

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
            throw new NotificationException("Failed to send email: " + e.getMessage());
        }
    }

    @PostMapping("/generate")
    public ResponseEntity<String> generateOTP(@RequestBody EmailRequest emailRequest) {
        System.out.print("here in otp sender");
        String result = otpService.sendOtp(emailRequest);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/verify")
    public ResponseEntity<String> verifyOTP(@RequestBody OtpVerifyRequest otpVerifyRequest) {
        System.out.println("here in verify ");
        String isValid = otpService.verifyOTP(otpVerifyRequest.getEmail(), otpVerifyRequest.getOtpCode());
        return ResponseEntity.ok(isValid);
    }

    @PostMapping("/validateEmail")
    public ResponseEntity<String> validateEmail(@RequestBody EmailValidateRequest emailValidateRequest){
        System.out.println("here in validator"+emailValidateRequest.getEmail());
        String isValid = otpService.verifyEmailToken(emailValidateRequest.getEmail(),emailValidateRequest.getEmailToken());
        return ResponseEntity.ok(isValid);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleValidationExceptions(MethodArgumentNotValidException ex) {
        return ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
    }
}
