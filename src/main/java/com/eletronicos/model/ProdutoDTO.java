package com.eletronicos.model;

import java.math.BigDecimal;

/**
 * DTO (Data Transfer Object) para a entidade Produto.
 * Usado para transferir dados de produtos de forma segura para o frontend.
 */
public class ProdutoDTO {

    private Long id;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private int estoque;
    private String imagem;
    private String marca;
    private String modelo;
    private boolean destaque;
    private String nomeCategoria; // Apenas o nome da categoria, para simplificar

    // Construtor vazio é necessário para frameworks como o Jackson
    public ProdutoDTO() {
    }

    // Construtor para facilitar a conversão da Entidade para DTO
    public ProdutoDTO(Produto produto) {
        this.id = produto.id;
        this.nome = produto.getNome();
        this.descricao = produto.getDescricao();
        this.preco = produto.getPreco();
        this.estoque = produto.getEstoque();
        this.imagem = produto.getImagem();
        this.marca = produto.getMarca();
        this.modelo = produto.getModelo();
        this.destaque = produto.getDestaque();
        if (produto.getCategoria() != null) {
            this.nomeCategoria = produto.getCategoria().getNome();
        }
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public int getEstoque() {
        return estoque;
    }

    public void setEstoque(int estoque) {
        this.estoque = estoque;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public boolean isDestaque() {
        return destaque;
    }

    public void setDestaque(boolean destaque) {
        this.destaque = destaque;
    }

    public String getNomeCategoria() {
        return nomeCategoria;
    }

    public void setNomeCategoria(String nomeCategoria) {
        this.nomeCategoria = nomeCategoria;
    }
}
