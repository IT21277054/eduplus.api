package com.ds.assignment.paymentservice.controller;

import com.ds.assignment.paymentservice.dto.PaymentRequest;
import com.ds.assignment.paymentservice.dto.PaymentResponse;
import com.ds.assignment.paymentservice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/api/enroll")
@RequiredArgsConstructor
public class EnrollToCourse {

    private final PaymentService paymentService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void addCoursetoUser(@RequestBody PaymentRequest paymentRequest) {
        paymentService.enrollUser(paymentRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PaymentResponse> getAllPayments() {
        return paymentService.getAllPayments();
    }

}
