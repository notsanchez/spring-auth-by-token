package com.example.esyfinancebackend.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.example.esyfinancebackend.dto.ErrorDTO;
import com.example.esyfinancebackend.model.User;
import com.example.esyfinancebackend.repository.UserRepository;
import com.example.esyfinancebackend.security.JWTDecoderService;
import com.example.esyfinancebackend.security.TokenService;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    public ErrorDTO errorDTO;

    @GetMapping("/api/v1/customer/user")
    List<User> getUsers(@RequestHeader("Authorization") String auth) {

        if (auth == null || auth.isEmpty()) {
            return new ArrayList<>();
        } else {

            var users = userRepository.findAll();

            if (users == null || users.isEmpty()) {
                return new ArrayList<>();
            } else {
                return users;
            }
        }
    }

    @GetMapping("/api/v1/customer/user-details")
    Object getUserDetails(@RequestHeader("Authorization") String auth) {

        if (auth == null || auth.isEmpty()) {
            ErrorDTO response = new ErrorDTO("true", "Invalid Token");

            return response;
        } else {

            var decrypt = TokenService.DecryptToken(auth);

            if (decrypt == null) {
                ErrorDTO response = new ErrorDTO("true", "Invalid token or expired");

                return response;
            } else {
                var user = userRepository.userDetails(decrypt);

                if (user == null) {
                    ErrorDTO response = new ErrorDTO("true", "No users found");

                    return response;
                } else {
                    return user;
                }
            }

        }

    }

}
