package com.ds.assignment.coursemanagementservice.repository;

import com.ds.assignment.coursemanagementservice.model.Quiz;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface QuizRepository extends MongoRepository<Quiz, String> {
}
