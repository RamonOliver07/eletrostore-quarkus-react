package com.eletronicos.resource;

import com.eletronicos.model.Usuario;
import com.eletronicos.model.UsuarioDTO;
import com.eletronicos.service.UsuarioService;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.List;

@Path("/api/usuarios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsuarioResource {

    @Inject
    UsuarioService usuarioService;

    @GET
    @Path("/perfil")
    @RolesAllowed({"usuario", "admin"})
    public Response obterPerfil(@Context SecurityContext securityContext) {
        String email = securityContext.getUserPrincipal().getName();
        return usuarioService.buscarPorEmail(email)
                .map(usuario -> {
                    // Remover senha por segurança
                    usuario.setSenha(null);
                    return Response.ok(usuario).build();
                })
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @POST
    @Path("/cadastro")
    @PermitAll
    @Transactional
    public Response cadastrar(@Valid UsuarioDTO usuarioDTO) { // <--- A CORREÇÃO PRINCIPAL ESTÁ AQUI
        System.out.println("--- DENTRO DO RESOURCE ---");
       

        try {
            Usuario novoUsuario = new Usuario();
            novoUsuario.setNome(usuarioDTO.getNome());
            novoUsuario.setEmail(usuarioDTO.getEmail());
            novoUsuario.setSenha(usuarioDTO.getSenha());


            usuarioService.cadastrar(novoUsuario);

            // Retorna uma resposta segura (sem a senha)
            novoUsuario.setSenha(null);
            return Response.status(Response.Status.CREATED).entity(novoUsuario).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Erro ao cadastrar usuário: " + e.getMessage())
                    .build();
        }
    }

    @PUT
    @Path("/perfil")
    @Transactional
    @RolesAllowed({"usuario", "admin"})
    public Response atualizarPerfil(@Valid Usuario usuario, @Context SecurityContext securityContext) {
        String email = securityContext.getUserPrincipal().getName();
        return usuarioService.atualizarPerfil(email, usuario)
                .map(u -> {
                    // Remover senha por segurança
                    u.setSenha(null);
                    return Response.ok(u).build();
                })
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @GET
    @Path("/todos")
    @RolesAllowed("admin")
    public List<Usuario> listarTodos() {
        List<Usuario> usuarios = usuarioService.listarTodos();
        // Remover senhas por segurança
        usuarios.forEach(u -> u.setSenha(null));
        return usuarios;
    }
}