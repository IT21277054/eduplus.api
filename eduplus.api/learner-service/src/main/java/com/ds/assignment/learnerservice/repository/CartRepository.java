package com.ds.assignment.learnerservice.repository;

import com.ds.assignment.learnerservice.model.Cart;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CartRepository extends MongoRepository<Cart, String> {

    void deleteByAccountIdAndCourseId(String accountId, String courseId);
}
