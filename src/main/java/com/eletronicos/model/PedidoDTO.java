package com.eletronicos.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * DTO (Data Transfer Object) para a entidade Pedido.
 */
public class PedidoDTO {

    private Long id;
    private LocalDateTime dataPedido;
    private BigDecimal valorTotal;
    private String status;
    private String nomeUsuario; // Apenas o nome do usuário, não o objeto inteiro
    private List<ItemPedidoDTO> itens; // Usará um DTO para os itens também

    // Construtor vazio
    public PedidoDTO() {
    }

    // Construtor para converter a Entidade em DTO
    public PedidoDTO(Pedido pedido) {
        this.id = pedido.id;
        this.dataPedido = pedido.getDataPedido();
        this.valorTotal = pedido.getValorTotal();
        // CORREÇÃO: Converte o Enum StatusPedido para uma String
        if (pedido.getStatus() != null) {
            this.status = pedido.getStatus().name();
        }
        if (pedido.getUsuario() != null) {
            this.nomeUsuario = pedido.getUsuario().getNome();
        }
        // Converte a lista de entidades ItemPedido para uma lista de ItemPedidoDTO
        if (pedido.getItens() != null) {
            this.itens = pedido.getItens().stream()
                            .map(ItemPedidoDTO::new)
                            .collect(Collectors.toList());
        }
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(LocalDateTime dataPedido) {
        this.dataPedido = dataPedido;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public List<ItemPedidoDTO> getItens() {
        return itens;
    }

    public void setItens(List<ItemPedidoDTO> itens) {
        this.itens = itens;
    }
}
