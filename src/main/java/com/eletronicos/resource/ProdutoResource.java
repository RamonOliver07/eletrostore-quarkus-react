package com.eletronicos.resource;

import com.eletronicos.model.Produto;
import com.eletronicos.service.ProdutoService;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/api/produtos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProdutoResource {

    @Inject
    ProdutoService produtoService;

    @GET
    public List<Produto> listarTodos() {
        return produtoService.listarTodos();
    }

    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Long id) {
        return produtoService.buscarPorId(id)
                .map(produto -> Response.ok(produto).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @GET
    @Path("/categoria/{categoriaId}")
    public List<Produto> buscarPorCategoria(@PathParam("categoriaId") Long categoriaId) {
        return produtoService.buscarPorCategoria(categoriaId);
    }

    @GET
    @Path("/busca")
    public List<Produto> buscarPorNome(@QueryParam("nome") String nome) {
        return produtoService.buscarPorNome(nome);
    }

    @GET
    @Path("/destaques")
    public List<Produto> listarDestaques() {
        return produtoService.listarDestaques();
    }

    @POST
    @Transactional
    @RolesAllowed("admin")
    public Response criar(@Valid Produto produto) {
        produtoService.salvar(produto);
        return Response.status(Response.Status.CREATED).entity(produto).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    @RolesAllowed("admin")
    public Response atualizar(@PathParam("id") Long id, @Valid Produto produto) {
        return produtoService.atualizar(id, produto)
                .map(p -> Response.ok(p).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    @RolesAllowed("admin")
    public Response deletar(@PathParam("id") Long id) {
        boolean removido = produtoService.deletar(id);
        return removido 
                ? Response.noContent().build() 
                : Response.status(Response.Status.NOT_FOUND).build();
    }
}