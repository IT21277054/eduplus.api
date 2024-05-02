package com.ds.assignment.notificationservice.service.impl;

import com.ds.assignment.notificationservice.config.TwilioConfig;
import com.ds.assignment.notificationservice.dto.EmailRequest;
import com.ds.assignment.notificationservice.dto.SmsRequest;
import com.ds.assignment.notificationservice.service.NotificationService;
import com.twilio.exception.ApiException;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.time.Duration;


@Service
public class NotificationServiceImpl implements NotificationService {
    @Autowired
    TwilioConfig twilioConfig;
    @Autowired
    private JavaMailSender javaMailSender;
    @Override
    public String sendSms(SmsRequest smsRequest) {
        PhoneNumber toPhoneNumber = new PhoneNumber(smsRequest.getPhoneNumber());
        PhoneNumber fromPhoneNumber = new PhoneNumber(twilioConfig.getTrialNumber());
        String textMessage = smsRequest.getMessage();

        try {
            Message message = Message
                    .creator(toPhoneNumber, fromPhoneNumber, textMessage)
                    .create();
            if (message != null && message.getSid() != null) {
                return "message sent";
            } else {
                return "message sending failed";
            }
        } catch (ApiException e) {
            e.printStackTrace();
            return "message sending failed: " + e.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
            return "message sending failed: " + e.getMessage();
        }
    }

    @Override
    public void sendEmail(EmailRequest emailRequest) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(emailRequest.getTo());
        message.setSubject(emailRequest.getSubject());
        message.setText(emailRequest.getBody());

        try {
            javaMailSender.send(message);
            System.out.println("Email sent successfully.");
        } catch (MailException e) {
            System.err.println("Failed to send email: " + e.getMessage());
        }
    }

}
