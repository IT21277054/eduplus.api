package com.ds.assignment.paymentservice.repository;

import com.ds.assignment.paymentservice.models.Payment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends MongoRepository <Payment, String> {
}
