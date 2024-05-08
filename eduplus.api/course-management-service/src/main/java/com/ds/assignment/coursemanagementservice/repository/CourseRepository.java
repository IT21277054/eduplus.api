package com.ds.assignment.coursemanagementservice.repository;

import com.ds.assignment.coursemanagementservice.model.Course;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends MongoRepository<Course, String> {

    List<Course> findByStatus(String status);

}
