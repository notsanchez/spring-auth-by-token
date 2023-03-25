package com.example.esyfinancebackend.security;

import java.time.Instant;

import org.springframework.stereotype.Service;

@Service
public class JWTDecoderService {

    public static int key = 2;

    public static String EncryptJWT(String publicKey) {

        // Encrypt publicKey

        String encrypted = "";

        int key = JWTDecoderService.key;

        char[] chars = publicKey.toCharArray();

        for (char c : chars) {
            c += key;

            encrypted += c;

        }

        // Generate and encrypt = token|expDate

        Instant now = Instant.now();

        Instant plus24Hours = now.plusSeconds(15);

        long expDate = plus24Hours.getEpochSecond();

        var token = encrypted + "|" + expDate;

        String tokenEncrypted = "";

        char[] tokenChars = token.toCharArray();

        for (char c : tokenChars) {
            c += key;

            tokenEncrypted += c;

        }

        return tokenEncrypted;

    }

    public static String DecryptJWT(String publicKey) {

        // Decrypt token = publicKeyEncrypted|expDate

        String decrypted = "";

        int key = JWTDecoderService.key;

        char[] chars = publicKey.toCharArray();

        for (char c : chars) {
            c -= key;

            decrypted += c;

        }

        // Split string separete with pipe
        String[] parts = decrypted.split("\\|");

        // Decrypt publicKey

        char[] tokenChars = parts[0].toCharArray();

        String decryptedToken = "";

        for (char c : tokenChars) {
            c -= key;

            decryptedToken += c;

        }

        // Verify if token is valid

        Instant now = Instant.now();

        long epochNow = now.getEpochSecond();

        int parseExp = Integer.parseInt(parts[1]);

        if (epochNow <= parseExp) {
            return decryptedToken;
        } else {
            return null;
        }

    }

}
