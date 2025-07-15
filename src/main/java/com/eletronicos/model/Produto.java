package com.eletronicos.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
public class Produto extends PanacheEntity {

    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    @NotBlank(message = "A descrição é obrigatória")
    @Column(length = 2000)
    private String descricao;

    @NotNull(message = "O preço é obrigatório")
    @Min(value = 0, message = "O preço deve ser um valor positivo")
    private BigDecimal preco;

    @NotNull(message = "A quantidade em estoque é obrigatória")
    @Min(value = 0, message = "A quantidade em estoque deve ser um valor positivo")
    private Integer estoque;

    private String imagem;
    
    private String marca;
    
    private String modelo;
    
    private Boolean destaque = false;

    @ManyToOne
    private Categoria categoria;

    // Getters e Setters
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

    public Integer getEstoque() {
        return estoque;
    }

    public void setEstoque(Integer estoque) {
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
    
    public Boolean getDestaque() {
        return destaque;
    }
    
    public void setDestaque(Boolean destaque) {
        this.destaque = destaque;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
}