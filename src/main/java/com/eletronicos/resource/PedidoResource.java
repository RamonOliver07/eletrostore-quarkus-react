package com.eletronicos.resource;

import com.eletronicos.model.Pedido;
import com.eletronicos.dto.PedidoDTO;
import com.eletronicos.formdto.PedidoFormDTO;
import com.eletronicos.service.PedidoService;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.List;
import java.util.stream.Collectors;

@Path("/api/pedidos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PedidoResource {

    @Inject
    PedidoService pedidoService;

    @GET
    public Response listarPedidosDoUsuario(@Context SecurityContext ctx) {
        // VERIFICAÇÃO: Garante que há um utilizador logado
        if (ctx.getUserPrincipal() == null) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        
        String emailUsuario = ctx.getUserPrincipal().getName();
        List<Pedido> pedidos = pedidoService.listarPorUsuario(emailUsuario);

        List<PedidoDTO> pedidosDTO = pedidos.stream()
                                            .map(pedido -> new PedidoDTO(pedido))
                                            .collect(Collectors.toList());
        
        return Response.ok(pedidosDTO).build();
    }

    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Long id, @Context SecurityContext ctx) {
        // VERIFICAÇÃO: Garante que há um utilizador logado
        if (ctx.getUserPrincipal() == null) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        
        String emailUsuario = ctx.getUserPrincipal().getName();
        
        return pedidoService.buscarPorIdEUsuario(id, emailUsuario)
                .map(pedido -> Response.ok(new PedidoDTO(pedido)).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @POST
    @Transactional
    public Response criarPedido(PedidoFormDTO dto, @Context SecurityContext ctx) {
        // VERIFICAÇÃO: Garante que há um utilizador logado
        if (ctx.getUserPrincipal() == null) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        
        try {
            String emailUsuario = ctx.getUserPrincipal().getName();
            Pedido pedidoCriado = pedidoService.criarPedido(dto, emailUsuario);
            return Response.status(Response.Status.CREATED).entity(new PedidoDTO(pedidoCriado)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
}
