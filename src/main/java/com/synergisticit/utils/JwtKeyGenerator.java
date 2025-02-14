package com.synergisticit.utils;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;

public class JwtKeyGenerator {
    public static void main(String[] args) {
        // Generate a secret key for the HS256 algorithm
        SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
        // Print the key in a Base64-encoded format
        String encodedKey = io.jsonwebtoken.io.Encoders.BASE64.encode(key.getEncoded());
        System.out.println("Your SuperSecretKeyForJWTGeneration: " + encodedKey);
    }
}