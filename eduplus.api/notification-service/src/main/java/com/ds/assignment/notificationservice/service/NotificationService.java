package com.ds.assignment.notificationservice.service;

import com.ds.assignment.notificationservice.dto.EmailRequest;
import com.ds.assignment.notificationservice.dto.SmsRequest;
import org.springframework.stereotype.Service;

public interface NotificationService {
   String sendSms(SmsRequest smsRequest);
   void sendEmail(EmailRequest emailRequest);

}
