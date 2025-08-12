package com.zalandolite.auth_service.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

/**
 * Utility class for handling JSON Web Tokens (JWT).
 * It is responsible for generating, validating, and parsing JWTs.
 */
@Component
public class JwtTokenProvider {

    // A secure key for signing the JWT. This should be a secret and
    // should not be hardcoded in a real-world application.
    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // Token expiration time in milliseconds (e.g., 10 days)
    private final long EXPIRATION_TIME = 864_000_000;

    /**
     * Generates a JWT token for a given user ID.
     * @param userId The unique identifier of the user.
     * @return A signed JWT token string.
     */
    public String generateToken(String userId) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME);

        return Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key)
                .compact();
    }
}