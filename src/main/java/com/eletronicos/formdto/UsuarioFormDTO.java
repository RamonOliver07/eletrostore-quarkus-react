package com.eletronicos.formdto;

/**
 * DTO (Data Transfer Object) para receber dados do formulário de cadastro de Utilizador.
 */
public class UsuarioFormDTO {

    private String nome;
    private String email;
    private String senha;

    // Getters e Setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
