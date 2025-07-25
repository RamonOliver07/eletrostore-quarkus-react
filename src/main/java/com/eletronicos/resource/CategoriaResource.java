package com.eletronicos.resource;

import com.eletronicos.model.Categoria;
import com.eletronicos.dto.CategoriaDTO;
import com.eletronicos.formdto.CategoriaFormDTO;
import com.eletronicos.service.CategoriaService;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Path("/api/categorias")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CategoriaResource {

    @Inject
    CategoriaService categoriaService;

    // --- Endpoints Públicos (GET) ---

    @GET
    public List<CategoriaDTO> listarTodas() {
        List<Categoria> categorias = categoriaService.listarTodas();
        return categorias.stream()
                .map(categoria -> new CategoriaDTO(categoria))
                .collect(Collectors.toList());
    }

    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Long id) {
        Optional<Categoria> categoriaOpt = categoriaService.buscarPorId(id);

        if (categoriaOpt.isPresent()) {
            CategoriaDTO dto = new CategoriaDTO(categoriaOpt.get());
            return Response.ok(dto).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    // --- Endpoints de Administração (Protegidos) ---

    @POST
    @Transactional
    public Response criar(CategoriaFormDTO dto, @Context SecurityContext ctx) {
        // VERIFICAÇÃO DE SEGURANÇA
        if (ctx.getUserPrincipal() == null || !ctx.isUserInRole("admin")) {
            return Response.status(Response.Status.FORBIDDEN).entity("Acesso negado.").build();
        }
        Categoria categoriaCriada = categoriaService.criar(dto);
        return Response.status(Response.Status.CREATED).entity(new CategoriaDTO(categoriaCriada)).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response atualizar(@PathParam("id") Long id, CategoriaFormDTO dto, @Context SecurityContext ctx) {
        // VERIFICAÇÃO DE SEGURANÇA
        if (ctx.getUserPrincipal() == null || !ctx.isUserInRole("admin")) {
            return Response.status(Response.Status.FORBIDDEN).entity("Acesso negado.").build();
        }
        Optional<Categoria> categoriaAtualizada = categoriaService.atualizar(id, dto);
        if (categoriaAtualizada.isPresent()) {
            return Response.ok(new CategoriaDTO(categoriaAtualizada.get())).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deletar(@PathParam("id") Long id, @Context SecurityContext ctx) {
        // VERIFICAÇÃO DE SEGURANÇA
        if (ctx.getUserPrincipal() == null || !ctx.isUserInRole("admin")) {
            return Response.status(Response.Status.FORBIDDEN).entity("Acesso negado.").build();
        }
        if (categoriaService.deletar(id)) {
            return Response.noContent().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
