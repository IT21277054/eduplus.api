package com.ds.assignment.repository;


import com.ds.assignment.model.UserProfile;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserProfileRepository extends MongoRepository<UserProfile,Integer> {
}
