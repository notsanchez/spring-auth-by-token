package com.example.esyfinancebackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.esyfinancebackend.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT * FROM user WHERE user.public_key = ?1", nativeQuery = true)
    User userDetails(String auth);


}
