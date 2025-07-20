package com.eletronicos.model;

import java.math.BigDecimal;

/**
 * DTO (Data Transfer Object) para a entidade ItemPedido.
 */
public class ItemPedidoDTO {

    private String nomeProduto;
    private int quantidade;
    private BigDecimal precoUnitario;

    // Construtor vazio
    public ItemPedidoDTO() {
    }

    // Construtor para converter a Entidade em DTO
    public ItemPedidoDTO(ItemPedido itemPedido) {
        if (itemPedido.getProduto() != null) {
            this.nomeProduto = itemPedido.getProduto().getNome();
        }
        this.quantidade = itemPedido.getQuantidade();
        this.precoUnitario = itemPedido.getPrecoUnitario();
    }

    // Getters e Setters
    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(BigDecimal precoUnitario) {
        this.precoUnitario = precoUnitario;
    }
}
