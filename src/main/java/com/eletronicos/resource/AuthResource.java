package com.eletronicos.resource;

import com.eletronicos.model.Usuario;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.smallrye.jwt.build.Jwt;
import javax.annotation.security.PermitAll;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashSet;

@Path("/api/auth")
public class AuthResource {

    @ConfigProperty(name = "mp.jwt.verify.issuer")
    String issuer;

    @POST
    @Path("/login")
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response login(LoginRequest loginRequest) {
        Usuario usuario = Usuario.find("email", loginRequest.email).firstResult();
        
        if (usuario == null || !BcryptUtil.matches(loginRequest.senha, usuario.getSenha())) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        
        String token = generateToken(usuario.getEmail(), usuario.getPapel(), 24 * 60 * 60); // 24 horas
        
        return Response.ok(new TokenResponse(token, usuario.getNome(), usuario.getPapel())).build();
    }
    
    private String generateToken(String email, String role, long duration) {
        return Jwt.issuer(issuer)
                .subject(email)
                .groups(new HashSet<>(Arrays.asList(role)))
                .expiresIn(Duration.ofSeconds(duration))
                .sign();
    }
    
    public static class LoginRequest {
        public String email;
        public String senha;
    }
    
    public static class TokenResponse {
        public String token;
        public String nome;
        public String papel;
        
        public TokenResponse(String token, String nome, String papel) {
            this.token = token;
            this.nome = nome;
            this.papel = papel;
        }
    }
}