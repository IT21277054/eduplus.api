// UnitRepository.java
package com.ds.assignment.coursemanagementservice.repository;

import com.ds.assignment.coursemanagementservice.model.Unit;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UnitRepository extends MongoRepository<Unit, String> {
    Unit findByCourseId(String courseId);
}