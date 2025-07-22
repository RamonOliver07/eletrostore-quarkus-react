package com.eletronicos.service;

import com.eletronicos.model.ItemPedido;
import com.eletronicos.model.Pedido;
import com.eletronicos.model.Produto;
import com.eletronicos.model.StatusPedido;
import com.eletronicos.model.Usuario;
import com.eletronicos.model.PedidoFormDTO;
import com.eletronicos.model.ItemPedidoFormDTO;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.WebApplicationException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class PedidoService {

    @Inject
    EntityManager em;

    public List<Pedido> listarPorUsuario(String email) {
        Usuario usuario = Usuario.find("email", email).firstResult();
        return Pedido.list("usuario", usuario);
    }

    // MÉTODO ADICIONADO QUE ESTAVA EM FALTA
    public Optional<Pedido> buscarPorId(Long id) {
        return Optional.ofNullable(Pedido.findById(id));
    }

    public Optional<Pedido> buscarPorIdEUsuario(Long id, String email) {
        Usuario usuario = Usuario.find("email", email).firstResult();
        if (usuario == null) {
            return Optional.empty();
        }
        Pedido pedido = Pedido.find("id = ?1 and usuario = ?2", id, usuario).firstResult();
        return Optional.ofNullable(pedido);
    }

    @Transactional
    public Pedido criarPedido(PedidoFormDTO dto, String emailUsuario) {
        Usuario usuario = Usuario.<Usuario>find("email", emailUsuario).firstResultOptional()
                .orElseThrow(() -> new WebApplicationException("Usuário não encontrado", 404));

        Pedido pedido = new Pedido();
        pedido.setUsuario(usuario);
        pedido.setDataPedido(LocalDateTime.now());
        pedido.setStatus(StatusPedido.PROCESSANDO);
        pedido.setMetodoPagamento(dto.getMetodoPagamento());
        pedido.setItens(new ArrayList<>());

        for (ItemPedidoFormDTO itemDTO : dto.getItens()) {
            Produto produto = Produto.<Produto>findByIdOptional(itemDTO.getProdutoId())
                    .orElseThrow(() -> new WebApplicationException("Produto com id " + itemDTO.getProdutoId() + " não encontrado.", 404));

            if (produto.getEstoque() < itemDTO.getQuantidade()) {
                throw new WebApplicationException("Estoque insuficiente para o produto: " + produto.getNome(), 400);
            }

            produto.setEstoque(produto.getEstoque() - itemDTO.getQuantidade());

            ItemPedido itemPedido = new ItemPedido();
            itemPedido.setProduto(produto);
            itemPedido.setQuantidade(itemDTO.getQuantidade());
            itemPedido.setPrecoUnitario(produto.getPreco());
            itemPedido.setPedido(pedido);
            pedido.getItens().add(itemPedido);
        }

        pedido.calcularTotal();
        
        em.persist(pedido);
        
        return pedido;
    }

    @Transactional
    public Optional<Pedido> atualizarStatus(Long id, StatusPedido novoStatus) {
        Optional<Pedido> pedidoOpt = Pedido.findByIdOptional(id);
        
        if (pedidoOpt.isPresent()) {
            Pedido pedido = pedidoOpt.get();
            pedido.setStatus(novoStatus);
            return Optional.of(pedido);
        }
        
        return Optional.empty();
    }
}
