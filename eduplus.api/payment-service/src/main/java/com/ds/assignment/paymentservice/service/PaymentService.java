package com.ds.assignment.paymentservice.service;

import com.ds.assignment.paymentservice.dto.PaymentRequest;
import com.ds.assignment.paymentservice.dto.PaymentResponse;
import com.ds.assignment.paymentservice.models.Payment;
import com.ds.assignment.paymentservice.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public void enrollUser(PaymentRequest paymentRequest) {
        Payment payment = Payment.builder()
                .courseId(paymentRequest.getCourseId())
                .userId(paymentRequest.getUserId())
                .amount(paymentRequest.getAmount())
                .date(LocalDate.now().toString())
                .build();

        paymentRepository.save(payment);
        log.info("Payment Saved");
    }

    public List<PaymentResponse> getAllPayments() {
        List<Payment> payments = paymentRepository.findAll();

        return payments.stream().map(this::mapToPaymentService).toList();
    }

    private PaymentResponse mapToPaymentService(Payment payment) {
        return PaymentResponse.builder()
                .courseId(payment.getCourseId())
                .userId(payment.getUserId())
                .amount(payment.getAmount())
                .date(payment.getDate())
                .build();
    }

}
