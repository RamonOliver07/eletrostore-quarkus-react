package com.eletronicos.bo;

import com.eletronicos.formdto.ItemPedidoFormDTO;
import com.eletronicos.formdto.PedidoFormDTO;
import com.eletronicos.model.ItemPedido;
import com.eletronicos.model.Pedido;
import com.eletronicos.model.Produto;
import com.eletronicos.model.StatusPedido;
import com.eletronicos.model.Usuario;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.WebApplicationException;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * BO (Business Object) para a entidade Pedido.
 * Contém todas as regras de negócio relacionadas a pedidos.
 */
@ApplicationScoped
public class PedidoBO {

    /**
     * Valida os dados de um novo pedido.
     * @param dto O DTO com os dados do formulário do pedido.
     */
    public void validarNovoPedido(PedidoFormDTO dto) {
        if (dto.getItens() == null || dto.getItens().isEmpty()) {
            throw new IllegalArgumentException("Um pedido deve conter pelo menos um item.");
        }
    }

    /**
     * Constrói uma entidade Pedido a partir de um DTO e de um Usuário.
     * @param dto O DTO com os dados do formulário.
     * @param usuario O utilizador que está a fazer o pedido.
     * @return Uma nova entidade Pedido pronta para ser persistida.
     */
    public Pedido construirPedido(PedidoFormDTO dto, Usuario usuario) {
        Pedido pedido = new Pedido();
        pedido.setUsuario(usuario);
        pedido.setDataPedido(LocalDateTime.now());
        pedido.setStatus(StatusPedido.PROCESSANDO);
        pedido.setMetodoPagamento(dto.getMetodoPagamento());
        pedido.setItens(new ArrayList<>());

        for (ItemPedidoFormDTO itemDTO : dto.getItens()) {
            Produto produto = Produto.<Produto>findByIdOptional(itemDTO.getProdutoId())
                    .orElseThrow(() -> new WebApplicationException("Produto com id " + itemDTO.getProdutoId() + " não encontrado.", 404));

            // Regra de negócio: Verificar stock
            if (produto.getEstoque() < itemDTO.getQuantidade()) {
                throw new WebApplicationException("Stock insuficiente para o produto: " + produto.getNome(), 400);
            }

            // Regra de negócio: Reduzir o stock
            produto.setEstoque(produto.getEstoque() - itemDTO.getQuantidade());

            ItemPedido itemPedido = new ItemPedido();
            itemPedido.setProduto(produto);
            itemPedido.setQuantidade(itemDTO.getQuantidade());
            itemPedido.setPrecoUnitario(produto.getPreco());
            itemPedido.setPedido(pedido);
            pedido.getItens().add(itemPedido);
        }

        // Regra de negócio: Calcular o total do pedido
        pedido.calcularTotal();
        
        return pedido;
    }
}
