package com.eletronicos.resource;

import com.eletronicos.model.Produto;
import com.eletronicos.dto.ProdutoDTO;
import com.eletronicos.formdto.ProdutoFormDTO;
import com.eletronicos.service.ProdutoService;

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

@Path("/api/produtos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProdutoResource {

    @Inject
    ProdutoService produtoService;

    // --- Endpoints Públicos (GET) ---

    @GET
    public List<ProdutoDTO> listarTodos() {
        List<Produto> produtos = produtoService.listarTodos();
        return produtos.stream()
                .map(produto -> new ProdutoDTO(produto))
                .collect(Collectors.toList());
    }

    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Long id) {
        return produtoService.buscarPorId(id)
                .map(produto -> Response.ok(new ProdutoDTO(produto)).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    // --- Endpoints de Administração (Protegidos) ---

    @POST
    @Transactional
    public Response criar(ProdutoFormDTO dto, @Context SecurityContext ctx) {
        // VERIFICAÇÃO DE SEGURANÇA: Apenas utilizadores com o papel "admin" podem passar
        if (ctx.getUserPrincipal() == null || !ctx.isUserInRole("admin")) {
            return Response.status(Response.Status.FORBIDDEN).entity("Acesso negado.").build();
        }
        Produto produtoCriado = produtoService.criar(dto);
        return Response.status(Response.Status.CREATED).entity(new ProdutoDTO(produtoCriado)).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response atualizar(@PathParam("id") Long id, ProdutoFormDTO dto, @Context SecurityContext ctx) {
        // VERIFICAÇÃO DE SEGURANÇA
        if (ctx.getUserPrincipal() == null || !ctx.isUserInRole("admin")) {
            return Response.status(Response.Status.FORBIDDEN).entity("Acesso negado.").build();
        }
        Optional<Produto> produtoAtualizado = produtoService.atualizar(id, dto);
        if (produtoAtualizado.isPresent()) {
            return Response.ok(new ProdutoDTO(produtoAtualizado.get())).build();
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
        if (produtoService.deletar(id)) {
            return Response.noContent().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
