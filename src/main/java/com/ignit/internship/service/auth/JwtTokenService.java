package com.ignit.internship.service.auth;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ignit.internship.model.auth.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public final class JwtTokenService {

    @Value("${security.jwt.secret-key}")
    private String secretKey;

    @Value("${security.jwt.expiration-time}")
    private Long expirationTime;

    public String buildToken(User user) {
        return Jwts.builder()
            .id(user.getId().toString())
            .subject(user.getUsername())
            .claim("email", user.getEmail())
            .issuedAt(Date.from(getCurrentDate().toInstant(ZoneOffset.UTC)))
            .expiration(Date.from(getExpirationDate().toInstant(ZoneOffset.UTC)))
            .signWith(getSecretKey())
            .compact();
    }

    public Claims parseToken(String token) throws Exception {
        Object temp = Jwts.parser()
            .verifyWith(getSecretKey())
            .build()
            .parse(token)
            .getPayload();
        if (temp instanceof Claims claims) {
            return claims;
        }
        else throw new Exception("Jwt invalid");
    }

    public SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }
 
    public Long getExpirationTime() {
        return expirationTime;
    }

    public LocalDateTime getCurrentDate() {
        return LocalDateTime.now(ZoneId.of("UTC"));
    }

    public LocalDateTime getExpirationDate() {
        return getCurrentDate().plus(Duration.ofMillis(expirationTime));
    }
}
