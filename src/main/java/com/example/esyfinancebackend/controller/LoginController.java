package com.example.esyfinancebackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.esyfinancebackend.dto.LoginDTO;
import com.example.esyfinancebackend.dto.TokenResponseDTO;
import com.example.esyfinancebackend.model.User;
import com.example.esyfinancebackend.repository.LoginRepository;
import com.example.esyfinancebackend.repository.UserRepository;
import com.example.esyfinancebackend.security.TokenService;

import lombok.var;

@RestController
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LoginRepository loginRepository;

    @PostMapping("/api/v1/auth/create-user")
    User registerNewUser(@RequestBody User newUser) {
        return userRepository.save(newUser);
    }

    private boolean isNull(Object obj) {
        return obj == null;
    }

    @PostMapping("/api/v1/auth/login")
    Object login(@RequestBody LoginDTO login) {

        var userResponse = loginRepository.loginUserByPhoneAndPassword(login.getPhone(), login.getPassword());

        if (isNull(userResponse)) {
            return null;
        } else {

            String publicKey = userResponse.getPublicKey();

            var encrypt = TokenService.EncryptToken(publicKey);

            TokenResponseDTO response = new TokenResponseDTO(encrypt);

            return response;
        }

    }

}
