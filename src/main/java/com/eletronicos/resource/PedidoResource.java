package com.eletronicos.resource;

import com.eletronicos.model.Pedido;
import com.eletronicos.model.PedidoDTO;
import com.eletronicos.model.PedidoFormDTO;
import com.eletronicos.service.PedidoService;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.List;

@Path("/api/pedidos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PedidoResource {

    @Inject
    PedidoService pedidoService;

    @GET
    public Response listarPedidosDoUsuario() {
        // Lógica de segurança removida. Retorna uma lista vazia por enquanto.
        // Mais tarde, poderíamos passar o ID do utilizador como parâmetro.
        return Response.ok(Collections.emptyList()).build();
    }

    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Long id) {
        // Lógica de segurança removida.
        return pedidoService.buscarPorId(id) // Precisaremos de um método buscarPorId no serviço
                .map(pedido -> Response.ok(new PedidoDTO(pedido)).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @POST
    @Transactional
    public Response criarPedido(PedidoFormDTO dto) {
        // Lógica de segurança removida. Assume um utilizador de teste por enquanto.
        // Isto terá de ser ajustado para obter o utilizador de outra forma.
        String emailUsuarioTeste = "cliente@exemplo.com";
        try {
            Pedido pedidoCriado = pedidoService.criarPedido(dto, emailUsuarioTeste);
            return Response.status(Response.Status.CREATED).entity(new PedidoDTO(pedidoCriado)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
}
