package com.ds.assignment.notificationservice.service.impl;

import com.ds.assignment.notificationservice.dto.EmailRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Random;
import java.util.concurrent.TimeUnit;
@Service
public class OtpService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private NotificationServiceImpl notificationService;

    private static final String OTP_PREFIX = "otp:";
    private static final String RATE_LIMIT_PREFIX = "rate_limit:";
    private static final int RATE_LIMIT = 5;
    private static final long RATE_LIMIT_PERIOD_SECONDS = 60;
    private final String possibleDictionary = "ABDEFGHKMNPQRSTUVWXYZabdefghkmnpqrstuvwxyz23456789";


    public String sendOtp(EmailRequest emailRequest) {
        // Check rate limiting
        String rateLimitKey = RATE_LIMIT_PREFIX + emailRequest.getTo();
        if (!isRateLimited(rateLimitKey)) {
            return "Rate limit exceeded. Please try again later.";
        }

        // Generate OTP
        String otpKey = OTP_PREFIX + emailRequest.getTo();
        String existingOTP = redisTemplate.opsForValue().get(otpKey);
        if (existingOTP != null) {
            return "An OTP has already been generated for this number. Please check your Email.";
        }

        String otpCode = generateRandomOTP(6);
        redisTemplate.opsForValue().set(otpKey, otpCode, Duration.ofMinutes(5));

        emailRequest.setBody(otpCode);

        notificationService.sendEmail(emailRequest);

        return "OTP generated and sent to " + emailRequest.getTo();
    }

    public String verifyOTP(String email, String otpCode) {
        String otpKey = OTP_PREFIX + email;
        String storedOTP = redisTemplate.opsForValue().get(otpKey);

        if (storedOTP != null && storedOTP.equals(otpCode)) {
            redisTemplate.delete(otpKey);
            String emailAuthKey = dictionaryTimeRandom(40);
            String emailTokenkey = email + "otp_token_key";

            redisTemplate.opsForValue().set(emailTokenkey, emailAuthKey, Duration.ofMinutes(3));
            return emailAuthKey;
        }

        return "OTP Verification failed ";
    }

    public String verifyEmailToken(String email, String emailKey){
        String emailTOkenkey = email + "otp_token_key";
        String token = redisTemplate.opsForValue().get(emailTOkenkey);

        if(token == null){
            throw new Error("Email Verification Failed");
        }
        if(token.equals(emailKey) && emailKey.length()>10 ){
            return email;
        }
        throw new Error("Email Verification Failed");
    }


    private String dictionaryTimeRandom(int len) {
        StringBuilder text = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < len; i++) {
            text.append(possibleDictionary.charAt(random.nextInt(possibleDictionary.length())));
        }
        return text.toString() + System.currentTimeMillis();
    }
    private boolean isRateLimited(String rateLimitKey) {
        Long currentCount = redisTemplate.opsForValue().increment(rateLimitKey, 1);
        if (currentCount == 1) {
            redisTemplate.expire(rateLimitKey, RATE_LIMIT_PERIOD_SECONDS, TimeUnit.SECONDS);
        }
        return currentCount <= RATE_LIMIT;
    }

    private String generateRandomOTP(int length) {
        String characters = "0123456789";
        StringBuilder otp = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            otp.append(characters.charAt(random.nextInt(characters.length())));
        }
        return otp.toString();
    }
}
