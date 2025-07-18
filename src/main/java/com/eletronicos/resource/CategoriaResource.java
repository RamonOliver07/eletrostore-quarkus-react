package com.eletronicos.resource;

import com.eletronicos.model.Categoria;
import com.eletronicos.model.CategoriaDTO; // <-- Importa o novo DTO
import com.eletronicos.service.CategoriaService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/api/categorias")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CategoriaResource {

    @Inject
    CategoriaService categoriaService;

    @GET
    public List<CategoriaDTO> listarTodas() {
        // 1. Busca a lista de entidades Categoria
        List<Categoria> categorias = categoriaService.listarTodas();
        
        // 2. Converte cada Categoria para um CategoriaDTO antes de retornar
        return categorias.stream()
                .map(CategoriaDTO::new) // Usa o construtor que criamos no DTO
                .collect(Collectors.toList());
    }

    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Long id) {
        return categoriaService.buscarPorId(id)
                .map(categoria -> {
                    // Converte a entidade encontrada para DTO
                    CategoriaDTO dto = new CategoriaDTO(categoria);
                    return Response.ok(dto).build();
                })
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    // --- MÉTODOS DE ADMINISTRAÇÃO ---
    // NOTA: Os métodos de POST, PUT e DELETE também deveriam ser refatorados
    // para receber DTOs, mas vamos focar em fazer a listagem funcionar primeiro.
    // Deixaremos os métodos abaixo como estão por enquanto.

    @POST
    public Response criar(Categoria categoria) {
        categoriaService.criar(categoria);
        return Response.status(Response.Status.CREATED).entity(categoria).build();
    }

    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") Long id, Categoria categoria) {
        return categoriaService.atualizar(id, categoria)
                .map(c -> Response.ok(c).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @DELETE
    @Path("/{id}")
    public Response deletar(@PathParam("id") Long id) {
        if (categoriaService.deletar(id)) {
            return Response.noContent().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
