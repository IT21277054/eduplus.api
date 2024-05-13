package com.ds.assignment.notificationservice.service.impl;

import com.ds.assignment.notificationservice.config.TwilioConfig;
import com.ds.assignment.notificationservice.dto.EmailRequest;
import com.ds.assignment.notificationservice.dto.SmsRequest;
import com.ds.assignment.notificationservice.exception.NotificationException;
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
        System.out.println("here");
        PhoneNumber toPhoneNumber = new PhoneNumber(smsRequest.getPhoneNumber());
        PhoneNumber fromPhoneNumber = new PhoneNumber(twilioConfig.getTrialNumber());
        String textMessage = smsRequest.getMessage();
        System.out.println(toPhoneNumber);
        System.out.println(fromPhoneNumber);
        try {
            Message message = Message
                    .creator(toPhoneNumber, fromPhoneNumber, textMessage)
                    .create();
            System.out.println(message);
            if (message != null && message.getSid() != null) {
                return "message sent";
            } else {
                throw new NotificationException("Failed to send sms: ");
            }
        } catch (ApiException e) {
            throw new NotificationException("Failed to send sms: " + e.getMessage());
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
            throw new NotificationException("Failed to send email: " + e.getMessage());
        }
    }

}
