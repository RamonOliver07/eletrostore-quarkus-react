package com.eletronicos.model;

/**
 * DTO (Data Transfer Object) para receber dados do formulário de criação/edição de Categoria.
 */
public class CategoriaFormDTO {

    private String nome;
    private String descricao;
    private String icone;

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

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }
}
