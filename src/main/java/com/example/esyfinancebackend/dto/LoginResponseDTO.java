package com.example.esyfinancebackend.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class LoginResponseDTO {

    public LoginResponseDTO() {
        super();
    }

    @Id
    private String publicKey;

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public LoginResponseDTO(String publicKey) {
        this.publicKey = publicKey;
    }

}
