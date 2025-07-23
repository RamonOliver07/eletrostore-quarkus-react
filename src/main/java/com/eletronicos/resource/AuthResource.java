package com.eletronicos.resource;

import com.eletronicos.model.Usuario;
import com.eletronicos.service.TokenService; 
import org.mindrot.jbcrypt.BCrypt;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/api/auth")
public class AuthResource {

    @Inject
    TokenService tokenService; // <-- Injeta o nosso novo TokenService

    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response login(LoginRequest loginRequest) {
        Usuario usuario = Usuario.find("email", loginRequest.email).firstResult();
        
        if (usuario == null || !BCrypt.checkpw(loginRequest.senha, usuario.getHashSenha())) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("E-mail ou senha inválidos.").build();
        }
        
        // --- MUDANÇA PRINCIPAL AQUI ---
        // Delega a geração do token para o TokenService
        String token = tokenService.generateToken(usuario);
        
        // Retorna uma resposta de sucesso com os dados do utilizador e o token
        return Response.ok(new AuthResponse(token, usuario.getNome(), usuario.getEmail(), usuario.getPapel())).build();
    }
    
    // DTO para receber os dados de login do frontend
    public static class LoginRequest {
        public String email;
        public String senha;
    }
    
    // DTO para enviar a resposta completa para o frontend
    public static class AuthResponse {
        public String token;
        public String nome;
        public String email;
        public String papel;
        
        public AuthResponse(String token, String nome, String email, String papel) {
            this.token = token;
            this.nome = nome;
            this.email = email;
            this.papel = papel;
        }
    }
}
