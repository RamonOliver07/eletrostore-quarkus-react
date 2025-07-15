package com.eletronicos.resource;

import com.eletronicos.model.Pedido;
import com.eletronicos.model.StatusPedido;
import com.eletronicos.service.PedidoService;
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

@Path("/api/pedidos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PedidoResource {

    @Inject
    PedidoService pedidoService;

    @GET
    @RolesAllowed({"usuario", "admin"})
    public List<Pedido> listarPedidosDoUsuario(@Context SecurityContext securityContext) {
        String email = securityContext.getUserPrincipal().getName();
        return pedidoService.listarPedidosDoUsuario(email);
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"usuario", "admin"})
    public Response buscarPorId(@PathParam("id") Long id, @Context SecurityContext securityContext) {
        String email = securityContext.getUserPrincipal().getName();
        return pedidoService.buscarPorIdEUsuario(id, email)
                .map(pedido -> Response.ok(pedido).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @POST
    @Transactional
    @RolesAllowed({"usuario", "admin"})
    public Response criar(@Valid Pedido pedido, @Context SecurityContext securityContext) {
        String email = securityContext.getUserPrincipal().getName();
        Pedido novoPedido = pedidoService.criarPedido(pedido, email);
        return Response.status(Response.Status.CREATED).entity(novoPedido).build();
    }

    @GET
    @Path("/todos")
    @RolesAllowed("admin")
    public List<Pedido> listarTodos() {
        return pedidoService.listarTodos();
    }

    @PUT
    @Path("/{id}/status")
    @Transactional
    @RolesAllowed("admin")
    public Response atualizarStatus(@PathParam("id") Long id, StatusUpdateRequest request) {
        return pedidoService.atualizarStatus(id, request.status)
                .map(p -> Response.ok(p).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }
    
    public static class StatusUpdateRequest {
        public StatusPedido status;
    }
}