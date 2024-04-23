package com.ds.assignment.notificationservice.controller;

import com.ds.assignment.notificationservice.dto.EmailRequest;
import com.ds.assignment.notificationservice.dto.SmsRequest;
import com.ds.assignment.notificationservice.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notification")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping("/sms")
    @ResponseStatus(HttpStatus.CREATED)
    public String sendSms(@RequestBody SmsRequest smsRequest){
        return notificationService.sendSms(smsRequest);
    }

    @PostMapping("/send-email")
    public String sendEmail(@RequestBody EmailRequest emailRequest) {
        notificationService.sendEmail(emailRequest);
        return "Email sent successfully";
    }
}
