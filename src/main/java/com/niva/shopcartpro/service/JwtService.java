package com.niva.shopcartpro.service;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

        

        private final long expirationTime = 1000 * 60 * 60; // 1 hour

        @Value("${jwt.secret}")
        private String secret;

        /*
         * private final SecretKey secretKey =
         * Keys.hmacShaKeyFor(
         * "my-shopcartpro-secret-key-1234567890123456"
         * .getBytes()
         * );
         */

        private SecretKey getSecretKey() {

                return Keys.hmacShaKeyFor(
                                secret.getBytes());
        }

        public String generateToken(String email, String role) {

                Date now = new Date();

                Date expiration = new Date(now.getTime() + expirationTime);

                return Jwts.builder()
                                .subject(email)
                                .claim("role", role)
                                .issuedAt(now)
                                .expiration(expiration)
                                .signWith(getSecretKey())
                                .compact();
        }

        public String extractEmail(String token) {

                return Jwts.parser()
                                .verifyWith(getSecretKey())
                                .build()
                                .parseSignedClaims(token)
                                .getPayload()
                                .getSubject();
        }

        public boolean isTokenValid(
                        String token,
                        UserDetails userDetails) {

                String email = extractEmail(token);

                return email.equals(userDetails.getUsername());
        }
}