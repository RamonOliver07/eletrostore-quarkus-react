package com.eletronicos.resource;

import java.util.Collections;
import java.util.Optional;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.eletronicos.dto.PedidoDTO;
import com.eletronicos.formdto.PedidoFormDTO;
import com.eletronicos.model.Pedido;
import com.eletronicos.service.PedidoService;

@Path("/api/pedidos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PedidoResource {

	@Inject
	PedidoService pedidoService;

	@GET
	public Response listarPedidosDoUsuario() {
		// A lógica de segurança será tratada pelo filtro.
		// Precisaremos de uma forma de obter o utilizador atual a partir do token.
		// Por agora, retorna uma lista vazia para que o endpoint funcione.
		return Response.ok(Collections.emptyList()).build();
	}

	@GET
	@Path("/{id}")
	public Response buscarPorId(@PathParam("id") Long id) {
		Optional<Pedido> pedidoOpt = pedidoService.buscarPorId(id);

		if (pedidoOpt.isPresent()) {
			PedidoDTO dto = new PedidoDTO(pedidoOpt.get());
			return Response.ok(dto).build();
		} else {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
	}

	@POST
	@Transactional
	public Response criarPedido(PedidoFormDTO dto) {
		// A lógica de segurança será tratada pelo filtro.
		// Assume um utilizador de teste por enquanto.
		String emailUsuarioTeste = "cliente@exemplo.com";
		try {
			Pedido pedidoCriado = pedidoService.criarPedido(dto, emailUsuarioTeste);
			return Response.status(Response.Status.CREATED).entity(new PedidoDTO(pedidoCriado)).build();
		} catch (Exception e) {
			return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
		}
	}
}
