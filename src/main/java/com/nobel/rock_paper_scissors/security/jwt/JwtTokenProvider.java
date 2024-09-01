package com.nobel.rock_paper_scissors.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.*;

@Component
public class JwtTokenProvider {

    private static final String USER_ID = "id";
    private static final String USER_NAME = "name";
    private static final String USER_ROLES = "roles";

    @Value("${jwt.token.secret}")
    private String secret;

    @Value("${jwt.token.expired}")
    private long validityInMilliseconds;

    public JwtTokenProvider(UserDetailsService userDetailsService) {
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @PostConstruct
    protected void init() {
        try {
            MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
            byte[] hash = sha256.digest(secret.getBytes(StandardCharsets.UTF_8));
            secret = Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error initializing JWT secret key", e);
        }
    }

    public String createToken(Long userId, String name, List<String> roles) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(USER_ID, userId);
        claims.put(USER_NAME, name);
        claims.put(USER_ROLES, roles);

        Instant now = Instant.now();
        Instant validity = now.plusMillis(validityInMilliseconds);

        Key key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(validity))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public Authentication getAuthentication(String token, HttpServletRequest request, HttpServletResponse response) {
        if (validateToken(token)) {
            Long userId = getUserId(token);
            String username = getUsername(token);
            String[] roles = getUserRoles(token);

//            JwtUser userDetails = new JwtUser(userId, username, "", roles);
            return new UsernamePasswordAuthenticationFilter().attemptAuthentication(request, response);
        }
        throw new JwtAuthenticationException();
    }

    public String getUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secret.getBytes(StandardCharsets.UTF_8))
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get(USER_NAME, String.class);
    }

    public String[] getUserRoles(String token) {
        List<String> roles = (List<String>) Jwts.parserBuilder()
                .setSigningKey(secret.getBytes(StandardCharsets.UTF_8))
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get(USER_ROLES, List.class);
        return roles.toArray(new String[0]);
    }

    public Long getUserId(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secret.getBytes(StandardCharsets.UTF_8))
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get(USER_ID, Long.class);
    }

    public Optional<String> resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return Optional.of(bearerToken.substring(7));
        }
        return Optional.empty();
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(secret.getBytes(StandardCharsets.UTF_8))
                    .build()
                    .parseClaimsJws(token);
            return claims.getBody().getExpiration().after(Date.from(Instant.now()));
        } catch (JwtException | IllegalArgumentException e) {
            throw new JwtAuthenticationException();
        }
    }
}
