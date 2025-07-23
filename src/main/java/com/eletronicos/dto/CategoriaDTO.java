package com.eletronicos.dto;

import com.eletronicos.model.Categoria;

/**
 * DTO (Data Transfer Object) para a entidade Categoria.
 * Usado para transferir dados de categorias de forma segura para o frontend.
 */
public class CategoriaDTO {

    private Long id;
    private String nome;
    private String descricao;
    private String icone;

    // Construtor vazio
    public CategoriaDTO() {
    }

    // Construtor para facilitar a conversão da Entidade para DTO
    public CategoriaDTO(Categoria categoria) {
        this.id = categoria.id;
        this.nome = categoria.getNome();
        this.descricao = categoria.getDescricao();
        this.icone = categoria.getIcone();
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

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }
}
