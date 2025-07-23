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

    // Chave secreta para assinar o token. Em produção, isto deveria vir de um ficheiro de configuração.
    // IMPORTANTE: Esta chave deve ser longa e complexa.
    private final String SECRET_KEY = "uma-chave-secreta-muito-longa-e-segura-para-o-seu-projeto-de-ecommerce";

    /**
     * Gera um token JWT para um utilizador.
     * @param usuario O utilizador para quem o token será gerado.
     * @return Uma string com o token JWT.
     */
    public String generateToken(Usuario usuario) {
        Instant now = Instant.now();
        SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));

        return Jwts.builder()
                .setSubject(usuario.getEmail())
                .claim("roles", new HashSet<>(Arrays.asList(usuario.getPapel()))) // <-- CORREÇÃO AQUI
                .claim("userName", usuario.getNome())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(Duration.ofHours(24))))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Valida um token JWT e retorna as suas informações (claims).
     * @param token A string do token a ser validado.
     * @return Um objeto Jws<Claims> se o token for válido.
     * @throws io.jsonwebtoken.JwtException se o token for inválido ou expirado.
     */
    public Jws<Claims> validateToken(String token) {
        SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
        return Jwts.parserBuilder()
                   .setSigningKey(key)
                   .build()
                   .parseClaimsJws(token);
    }
}
