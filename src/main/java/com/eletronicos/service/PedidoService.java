package com.eletronicos.service;

import com.eletronicos.model.ItemPedido;
import com.eletronicos.model.Pedido;
import com.eletronicos.model.Produto;
import com.eletronicos.model.StatusPedido;
import com.eletronicos.model.Usuario;
import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class PedidoService {

    public List<Pedido> listarTodos() {
        return Pedido.listAll();
    }

    // MUDANÇA: Renomeado para corresponder ao que o PedidoResource chama
    public List<Pedido> listarPorUsuario(String email) {
        Usuario usuario = Usuario.find("email", email).firstResult();
        return Pedido.list("usuario", usuario);
    }

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
    public Pedido criarPedido(Pedido pedido, String email) {
        Usuario usuario = Usuario.find("email", email).firstResult();
        pedido.setUsuario(usuario);
        
        // Verificar estoque e ajustar preços dos itens
        for (ItemPedido item : pedido.getItens()) {
            Produto produto = Produto.findById(item.getProduto().id);
            
            // Verifica se há estoque suficiente
            if (produto.getEstoque() < item.getQuantidade()) {
                throw new RuntimeException("Estoque insuficiente para o produto: " + produto.getNome());
            }
            
            // Atualiza o preço unitário com base no preço atual do produto
            item.setPrecoUnitario(produto.getPreco());
            
            // Reduz o estoque
            produto.setEstoque(produto.getEstoque() - item.getQuantidade());
            produto.persist();
            
            // Associa o item ao pedido
            item.setPedido(pedido);
        }
        
        // Calcula o valor total
        pedido.calcularTotal();
        
        // Persiste o pedido
        pedido.persist();
        
        return pedido;
    }

    @Transactional
    public Optional<Pedido> atualizarStatus(Long id, StatusPedido novoStatus) {
        Optional<Pedido> pedidoOpt = buscarPorId(id);
        
        if (pedidoOpt.isPresent()) {
            Pedido pedido = pedidoOpt.get();
            pedido.setStatus(novoStatus);
            // O persist() não é necessário aqui, pois a entidade já está gerenciada
            return Optional.of(pedido);
        }
        
        return Optional.empty();
    }
}
