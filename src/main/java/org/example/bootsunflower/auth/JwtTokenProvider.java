package org.example.bootsunflower.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@Component
public class JwtTokenProvider {
    @Value("${jwt.secret}")
    private String secretKey;
    @Value("${jwt.expiration-ms}")
    private Long expirationMs;

    public SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public String generateToken(Authentication authentication) {
        String username = authentication.getName();
        Instant now = Instant.now();
        Date expiration = new Date(now.toEpochMilli() + expirationMs);
        Claims claims = Jwts.claims()
                .subject(username)
                .build();
        return Jwts.builder()
                .subject(username)
                .issuedAt(Date.from(now))
                .expiration(expiration)
                .claims(claims)
                .signWith(getSecretKey(), Jwts.SIG.HS256)
                .compact();
    }

    public String getUsername(String token) {
        return Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(getSecretKey())
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public Authentication getAuthentication(String token) {
        UserDetails user = new User(
                getUsername(token),
                "",
                List.of()
        );
        return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
    }
}