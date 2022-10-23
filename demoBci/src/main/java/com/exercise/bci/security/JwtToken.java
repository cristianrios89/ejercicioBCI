package com.exercise.bci.security;

import com.exercise.bci.exceptions.InvalidAuthenticationCredentialsException;
import com.exercise.bci.exceptions.UnexpectedException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;

import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

import static com.exercise.bci.enums.Constants.JWT_ISSUER;

@Data
@Component
public class JwtToken implements Token {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration.minutes}")
    private Long expirationInMinutes;

    @Override
    public String generate(String subject) throws UnexpectedException{
        try {
            JwtBuilder builder = Jwts.builder()
                    .setSubject(subject)
                    .setIssuer(JWT_ISSUER.getValue())
                    .setId(UUID.randomUUID().toString())
                    .setIssuedAt(Date.from(Instant.now()))
                    .setExpiration(Date.from(Instant.now().plus(expirationInMinutes, ChronoUnit.MINUTES)))
                    .signWith(Keys.hmacShaKeyFor(encodeSecret().getBytes()));
            return builder.compact();
        } catch (Exception e) {
            throw new UnexpectedException(e.getClass().getSimpleName() + ": " + e.getMessage());
        }

    }

    @Override
    public Claims getClaimsFromToken(String jwtToken) throws InvalidAuthenticationCredentialsException {
        try {
            return Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(encodeSecret().getBytes())).build()
                    .parseClaimsJws(jwtToken).getBody();
        } catch (Exception e) {
            throw new InvalidAuthenticationCredentialsException(e.getClass().getSimpleName() + ": " + e.getMessage());
        }
    }

    private String encodeSecret() throws NoSuchAlgorithmException {
        return Utils.encrypt(this.secret);
    }
}
