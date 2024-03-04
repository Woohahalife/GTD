package com.core.gtd.src.common.security.jwt;

import com.core.gtd.src.common.constatnt.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Slf4j
@Component
public class JwtTokenGenerator {

    private static final String BEARER = "Bearer";
    private SecretKey key;

    @Value("${jwt.access-expired}")
    private Long accessExpiredTimeMills;

    public JwtTokenGenerator(@Value("${jwt.secret}") String secret) {
        this.key = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
    }

    public JwtToken generateAccessToken(String username, Role role) {
        return JwtToken.builder()
                .accessToken(createJwtToken(username, role))
                .grantType(BEARER)
                .expiresIn(accessExpiredTimeMills)
                .build();
    }

    private String createJwtToken(String username, Role role) {

        return Jwts.builder()
                .claim("username", username)
                .claim("role", role.name())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+ accessExpiredTimeMills))
                .signWith(key)
                .compact();
    }

    // jwt로부터 username 추출
    public String getUsername(String token) {

        Claims claims = extractClaim(token, key);

        return claims.get("username", String.class);

    }

    // jwt로부터 role 추출
    public Role getRole(String token) {

        Claims claims = extractClaim(token, key);

        return claims.get("username", Role.class);
    }

    private Claims extractClaim(String token, SecretKey secretKey) {
        return Jwts.parser()
                .verifyWith(secretKey).build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
