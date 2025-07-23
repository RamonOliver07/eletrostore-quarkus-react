package com.eletronicos.dto;

import com.eletronicos.model.Pedido; // <-- IMPORT CORRIGIDO
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class PedidoDTO {

    private Long id;
    private LocalDateTime dataPedido;
    private BigDecimal valorTotal;
    private String status;
    private String nomeUsuario;
    private List<ItemPedidoDTO> itens;

    public PedidoDTO() {
    }

    public PedidoDTO(Pedido pedido) {
        this.id = pedido.id;
        this.dataPedido = pedido.getDataPedido();
        this.valorTotal = pedido.getValorTotal();
        if (pedido.getStatus() != null) {
            this.status = pedido.getStatus().name();
        }
        if (pedido.getUsuario() != null) {
            this.nomeUsuario = pedido.getUsuario().getNome();
        }
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
