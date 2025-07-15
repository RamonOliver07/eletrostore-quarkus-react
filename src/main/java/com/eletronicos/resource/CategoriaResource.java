package com.eletronicos.resource;

import com.eletronicos.model.Categoria;
import com.eletronicos.service.CategoriaService;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/api/categorias")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CategoriaResource {

    @Inject
    CategoriaService categoriaService;

    @GET
    public List<Categoria> listarTodas() {
        return categoriaService.listarTodas();
    }

    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Long id) {
        return categoriaService.buscarPorId(id)
                .map(categoria -> Response.ok(categoria).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @POST
    @Transactional
    @RolesAllowed("admin")
    public Response criar(@Valid Categoria categoria) {
        categoriaService.salvar(categoria);
        return Response.status(Response.Status.CREATED).entity(categoria).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    @RolesAllowed("admin")
    public Response atualizar(@PathParam("id") Long id, @Valid Categoria categoria) {
        return categoriaService.atualizar(id, categoria)
                .map(c -> Response.ok(c).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    @RolesAllowed("admin")
    public Response deletar(@PathParam("id") Long id) {
        boolean removido = categoriaService.deletar(id);
        return removido 
                ? Response.noContent().build() 
                : Response.status(Response.Status.NOT_FOUND).build();
    }
}