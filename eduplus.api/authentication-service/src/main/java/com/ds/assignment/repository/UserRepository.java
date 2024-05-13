package com.ds.assignment.repository;

import com.ds.assignment.model.User;
import com.ds.assignment.model.UserRole;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User,String> {
    @Query(value = "{'email' : ?0}", fields = "{ 'role': 1}")
    Optional<String> findRoleByEmail(String email);

    Optional<User> findByEmail(String email);

    boolean existsByRole(String admin);
}
