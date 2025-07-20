package com.eletronicos.resource;

import com.eletronicos.model.Pedido;
import com.eletronicos.model.PedidoDTO; // <-- Importa o DTO
import com.eletronicos.service.PedidoService;

import javax.inject.Inject;
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
    public Response listarPedidosDoUsuario(@Context SecurityContext securityContext) {
        String emailUsuario = securityContext.getUserPrincipal().getName();
        List<Pedido> pedidos = pedidoService.listarPorUsuario(emailUsuario);

        // Converte a lista de entidades para uma lista de DTOs
        List<PedidoDTO> pedidosDTO = pedidos.stream()
                                            .map(PedidoDTO::new)
                                            .collect(Collectors.toList());
        
        return Response.ok(pedidosDTO).build();
    }

    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Long id, @Context SecurityContext securityContext) {
        String emailUsuario = securityContext.getUserPrincipal().getName();
        
        return pedidoService.buscarPorIdEUsuario(id, emailUsuario)
                .map(pedido -> {
                    // Converte a entidade para DTO antes de enviar
                    PedidoDTO dto = new PedidoDTO(pedido);
                    return Response.ok(dto).build();
                })
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    // Outros métodos como POST, PUT, DELETE seriam refatorados de forma semelhante
}
