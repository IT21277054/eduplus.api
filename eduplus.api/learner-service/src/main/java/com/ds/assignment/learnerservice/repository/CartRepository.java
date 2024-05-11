package com.ds.assignment.learnerservice.repository;

import com.ds.assignment.learnerservice.model.Cart;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CartRepository extends MongoRepository<Cart,String> {
}
