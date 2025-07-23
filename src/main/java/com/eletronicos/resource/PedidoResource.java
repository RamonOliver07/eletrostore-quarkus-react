package com.eletronicos.resource;

import com.eletronicos.model.Pedido; // <-- CORRIGIDO
import com.eletronicos.dto.PedidoDTO;
import com.eletronicos.formdto.PedidoFormDTO;
import com.eletronicos.service.PedidoService;
import javax.annotation.security.RolesAllowed;
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
    @RolesAllowed({"usuario", "admin"})
    public Response listarPedidosDoUsuario(@Context SecurityContext securityContext) {
        String emailUsuario = securityContext.getUserPrincipal().getName();
        List<Pedido> pedidos = pedidoService.listarPorUsuario(emailUsuario);

        List<PedidoDTO> pedidosDTO = pedidos.stream()
                                            .map(PedidoDTO::new)
                                            .collect(Collectors.toList());
        
        return Response.ok(pedidosDTO).build();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"usuario", "admin"})
    public Response buscarPorId(@PathParam("id") Long id, @Context SecurityContext securityContext) {
        String emailUsuario = securityContext.getUserPrincipal().getName();
        
        return pedidoService.buscarPorIdEUsuario(id, emailUsuario)
                .map(pedido -> Response.ok(new PedidoDTO(pedido)).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @POST
    @Transactional
    @RolesAllowed({"usuario", "admin"})
    public Response criarPedido(PedidoFormDTO dto, @Context SecurityContext securityContext) {
        try {
            String emailUsuario = securityContext.getUserPrincipal().getName();
            Pedido pedidoCriado = pedidoService.criarPedido(dto, emailUsuario);
            // Retorna o DTO de visualização, não a entidade
            return Response.status(Response.Status.CREATED).entity(new PedidoDTO(pedidoCriado)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
}
