package com.example.esyfinancebackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.esyfinancebackend.dto.LoginResponseDTO;

public interface LoginRepository extends JpaRepository<LoginResponseDTO, String> {

    @Query(value = "SELECT public_key FROM user WHERE user.phone = ?1 AND user.password = ?2", nativeQuery = true)
    LoginResponseDTO loginUserByPhoneAndPassword(String phone, String password);

}
