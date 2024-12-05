package com.example.rbac.Utils;

import com.example.rbac.Models.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys; // For secret key
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtils {

    // Generate a secret key for HS512
    private final SecretKey jwtSecretKey = Keys.hmacShaKeyFor("yourSecretKeyHere1234567890123456".getBytes());

    private final int jwtExpirationMs = 86400000;

    // Generate JWT Token
    public String generateJwtToken(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return Jwts.builder()
                .subject(user.getUsername())
                .issuedAt(new Date())
                .expiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(jwtSecretKey,  Jwts.SIG.HS512) // Use SecretKey with SignatureAlgorithm
                .compact();
    }

    // Extract username from the JWT token
    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser() // Updated method
                .verifyWith(jwtSecretKey)
                .build() // Build the parser
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    // Validate the JWT token
    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser()
                    .verifyWith(jwtSecretKey)
                    .build()
                    .parseSignedClaims(authToken);
            return true;
        } catch (Exception e) {
            // Handle exceptions like ExpiredJwtException, UnsupportedJwtException, etc.
            e.printStackTrace(); // Log the exception for debugging
        }
        return false;
    }
}
