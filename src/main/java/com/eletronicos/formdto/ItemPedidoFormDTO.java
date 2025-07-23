package com.eletronicos.formdto;

/**
 * DTO (Data Transfer Object) para receber dados de um item de pedido
 * a partir do formulário/requisição do frontend.
 */
public class ItemPedidoFormDTO {

    private Long produtoId;
    private int quantidade;

    // Getters e Setters
    public Long getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Long produtoId) {
        this.produtoId = produtoId;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}
