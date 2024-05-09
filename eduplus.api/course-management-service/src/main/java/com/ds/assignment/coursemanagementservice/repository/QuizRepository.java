package com.ds.assignment.coursemanagementservice.repository;

import com.ds.assignment.coursemanagementservice.model.Quiz;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface QuizRepository extends MongoRepository<Quiz, String> {
    Quiz findByCourseId(String courseId);
}
