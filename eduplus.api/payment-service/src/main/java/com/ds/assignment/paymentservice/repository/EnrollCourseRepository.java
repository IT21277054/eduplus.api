package com.ds.assignment.paymentservice.repository;

import com.ds.assignment.paymentservice.models.UserProfile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnrollCourseRepository extends MongoRepository<UserProfile, String> {

    UserProfile findByAccountId(String userId);
}
