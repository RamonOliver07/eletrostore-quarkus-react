package com.eletronicos.formdto;

import java.util.List;

/**
 * DTO (Data Transfer Object) para receber dados do formulário de criação de um novo Pedido.
 */
public class PedidoFormDTO {

    private List<ItemPedidoFormDTO> itens;
    private String metodoPagamento;

    // Getters e Setters
    public List<ItemPedidoFormDTO> getItens() {
        return itens;
    }

    public void setItens(List<ItemPedidoFormDTO> itens) {
        this.itens = itens;
    }

    public String getMetodoPagamento() {
        return metodoPagamento;
    }

    public void setMetodoPagamento(String metodoPagamento) {
        this.metodoPagamento = metodoPagamento;
    }
}
