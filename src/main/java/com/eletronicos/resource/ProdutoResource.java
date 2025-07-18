package com.eletronicos.resource;

import com.eletronicos.model.ProdutoDTO; // <-- Importa o novo DTO
import com.eletronicos.model.Produto;
import com.eletronicos.service.ProdutoService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/api/produtos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProdutoResource {

    @Inject
    ProdutoService produtoService;

    @GET
    public List<ProdutoDTO> listarTodos() {
        // 1. Busca a lista de entidades
        List<Produto> produtos = produtoService.listarTodos();
        
        // 2. Converte cada Produto para um ProdutoDTO
        return produtos.stream()
                .map(ProdutoDTO::new) // Usa o construtor que criamos no DTO
                .collect(Collectors.toList());
    }

    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Long id) {
        return produtoService.buscarPorId(id)
                .map(produto -> {
                    // Converte a entidade encontrada para DTO antes de enviar
                    ProdutoDTO dto = new ProdutoDTO(produto);
                    return Response.ok(dto).build();
                })
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @GET
    @Path("/destaques")
    public List<ProdutoDTO> listarDestaques() {
        List<Produto> produtos = produtoService.listarDestaques();
        return produtos.stream()
                .map(ProdutoDTO::new)
                .collect(Collectors.toList());
    }
    
    // NOTA: Os métodos de POST, PUT e DELETE também deveriam ser refatorados
    // para receber DTOs, mas vamos focar em fazer a listagem funcionar primeiro.
    // Deixaremos os métodos abaixo como estão por enquanto.

    @POST
    public Response criar(Produto produto) {
        produtoService.criar(produto);
        return Response.status(Response.Status.CREATED).entity(produto).build();
    }

    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") Long id, Produto produto) {
        return produtoService.atualizar(id, produto)
                .map(p -> Response.ok(p).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @DELETE
    @Path("/{id}")
    public Response deletar(@PathParam("id") Long id) {
        if (produtoService.deletar(id)) {
            return Response.noContent().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
