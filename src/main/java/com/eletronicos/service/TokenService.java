package com.eletronicos.service;

import com.eletronicos.model.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import javax.enterprise.context.ApplicationScoped;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;

@ApplicationScoped
public class TokenService {

    private final String SECRET_KEY = "uma-chave-secreta-muito-longa-e-segura-para-o-seu-projeto-de-ecommerce";

    public String generateToken(Usuario usuario) {
        Instant now = Instant.now();
        SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));

        return Jwts.builder()
                .setSubject(usuario.getEmail())
                .claim("roles", new HashSet<>(Arrays.asList(usuario.getPapel())))
                .claim("userName", usuario.getNome())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(Duration.ofHours(24))))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public Jws<Claims> validateToken(String token) {
        SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
        return Jwts.parserBuilder()
                   .setSigningKey(key)
                   .build()
                   .parseClaimsJws(token);
    }

    // --- NOVO MÉTODO ADICIONADO ---
    /**
     * Extrai o e-mail (subject) de um token JWT.
     * @param token O token JWT no formato "Bearer [token]".
     * @return O e-mail do utilizador.
     */
    public String getEmailFromToken(String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return null;
        }
        String token = authorizationHeader.substring("Bearer ".length()).trim();
        try {
            Jws<Claims> claims = validateToken(token);
            return claims.getBody().getSubject();
        } catch (Exception e) {
            return null;
        }
    }
}
