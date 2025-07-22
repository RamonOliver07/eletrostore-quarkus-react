package com.eletronicos.resource;

import com.eletronicos.model.Usuario;
import org.mindrot.jbcrypt.BCrypt; 
import javax.annotation.security.PermitAll;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/api/auth")
public class AuthResource {

    @POST
    @Path("/login")
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response login(LoginRequest loginRequest) {
        Usuario usuario = Usuario.find("email", loginRequest.email).firstResult();
        
        // A verificação da senha continua a usar o jbcrypt
        if (usuario == null || !BCrypt.checkpw(loginRequest.senha, usuario.getSenha())) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("E-mail ou senha inválidos.").build();
        }
        
        // Se o login for bem-sucedido, retorna os dados do utilizador
        // O frontend usará isto para saber que o login foi um sucesso
        return Response.ok(new UserInfoResponse(usuario.getNome(), usuario.getEmail(), usuario.getPapel())).build();
    }
    
    // DTO para receber os dados de login do frontend
    public static class LoginRequest {
        public String email;
        public String senha;
    }
    
    // DTO para enviar os dados do utilizador para o frontend após o login
    public static class UserInfoResponse {
        public String nome;
        public String email;
        public String papel;
        
        public UserInfoResponse(String nome, String email, String papel) {
            this.nome = nome;
            this.email = email;
            this.papel = papel;
        }
    }
}
