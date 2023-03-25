package com.example.esyfinancebackend.security;

import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.time.Instant;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class TokenService {

    public static String secretKey = "hsW5mADzNGsAfPAs";
    public static int secondsToExpToken = 15;

    public static String EncryptToken(String publicKey) {

        // Generate and set token = publicKey | expDate

        Instant now = Instant.now();

        Instant plus24Hours = now.plusSeconds(secondsToExpToken);

        long expDate = plus24Hours.getEpochSecond();

        String token = publicKey + "|" + expDate;

        String algoritmo = "AES";
        byte[] bytesSecretKey = secretKey.getBytes(StandardCharsets.UTF_8);
        Key secret = new SecretKeySpec(bytesSecretKey, algoritmo);

        byte[] bytesToken = token.getBytes();

        try {
            Cipher c = Cipher.getInstance("AES/ECB/PKCS5Padding");

            c.init(Cipher.ENCRYPT_MODE, secret);
            byte[] bytesTokenEncrypted = c.doFinal(bytesToken);
            String encryptedToken = Base64.getEncoder().encodeToString(bytesTokenEncrypted);

            return encryptedToken;
        } catch (GeneralSecurityException e) {
            throw new IllegalStateException("Could not retrieve AES cipher", e);
        }

    }

    public static String DecryptToken(String token) {

        // Decrypt token = publicKeyEncrypted|expDate

        String algoritmo = "AES";
        byte[] bytesSecretKey = secretKey.getBytes(StandardCharsets.UTF_8);
        Key secret = new SecretKeySpec(bytesSecretKey, algoritmo);

        byte[] bytesTokenEncrypted = Base64.getDecoder().decode(token);

        try {
            Cipher c = Cipher.getInstance("AES/ECB/PKCS5Padding");
            c.init(Cipher.DECRYPT_MODE, secret);
            byte[] bytesTokenDecrypted = c.doFinal(bytesTokenEncrypted);
            String tokenDecrypted = new String(bytesTokenDecrypted);

            // Split string separete with pipe
            String[] parts = tokenDecrypted.split("\\|");

            // Verify if token is valid

            Instant now = Instant.now();

            long epochNow = now.getEpochSecond();

            int parseExp = Integer.parseInt(parts[1]);

            if (epochNow <= parseExp) {
                return parts[0];
            } else {
                return null;
            }

        } catch (GeneralSecurityException e) {
            throw new IllegalStateException("Could not retrieve AES cipher", e);
        }

    }

}
