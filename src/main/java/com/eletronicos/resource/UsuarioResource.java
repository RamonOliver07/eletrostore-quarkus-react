package com.eletronicos.resource;

import com.eletronicos.model.Usuario;
import com.eletronicos.model.UsuarioDTO;
import com.eletronicos.service.UsuarioService;
import org.mindrot.jbcrypt.BCrypt;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/api/usuarios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsuarioResource {

    @Inject
    UsuarioService usuarioService;

    @Inject
    EntityManager em;

    @POST
    @Path("/cadastro")
    @Transactional
    public Response cadastrar(@Valid UsuarioDTO usuarioDTO) {
        try {
            // Validação manual
            if (usuarioDTO.getSenha() == null || usuarioDTO.getSenha().isBlank()) {
                throw new WebApplicationException("A senha é obrigatória.", 400);
            }
            if (Usuario.find("email", usuarioDTO.getEmail()).firstResult() != null) {
                throw new WebApplicationException("E-mail já cadastrado.", 409);
            }

            // Criptografia
            String senhaCriptografada = BCrypt.hashpw(usuarioDTO.getSenha(), BCrypt.gensalt());

            // Criação da Entidade
            Usuario novoUsuario = new Usuario();
            novoUsuario.setNome(usuarioDTO.getNome());
            novoUsuario.setEmail(usuarioDTO.getEmail());
            novoUsuario.setSenha(senhaCriptografada);
            novoUsuario.setPapel("usuario");

            // Persistência
            em.persist(novoUsuario);

            // Retorno seguro
            novoUsuario.setSenha(null);
            return Response.status(Response.Status.CREATED).entity(novoUsuario).build();

        } catch (WebApplicationException e) {
            return Response.status(e.getResponse().getStatus()).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Erro ao cadastrar usuário: " + e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/todos")
    public List<Usuario> listarTodos() {
        List<Usuario> usuarios = usuarioService.listarTodos();
        // Remover senhas por segurança
        usuarios.forEach(u -> u.setSenha(null));
        return usuarios;
    }

    // Os métodos de perfil (obterPerfil, atualizarPerfil) foram removidos temporariamente
    // pois dependiam do SecurityContext, que foi desativado.
}
