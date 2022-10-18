package com.exercise.bci.security;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Data
@Component
public class JwtToken implements Token {
    @Value("${jwt.subject}")
    private String subject;

    @Value("${jwt.expiration.minutes}")
    private Long expirationInMinutes;

    @Override
    public String generate(Map<String, String> claims) {
        JwtBuilder builder = Jwts.builder()
                .setSubject(this.subject)
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plus(5l, ChronoUnit.MINUTES)))
                .signWith(Keys.secretKeyFor(SignatureAlgorithm.HS256));
        claims.forEach((k, v) -> builder.claim(k, v));
        return builder.compact();
    }

    @Override
    public Boolean isValid() {
        return true;
    }
}
