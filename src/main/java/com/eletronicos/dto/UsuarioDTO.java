package com.eletronicos.dto;

import com.eletronicos.model.Usuario; 

public class UsuarioDTO {

    private Long id;
    private String nome;
    private String email;
    private String papel;

    public UsuarioDTO() {
    }

    public UsuarioDTO(Usuario usuario) {
        this.id = usuario.id;
        this.nome = usuario.getNome();
        this.email = usuario.getEmail();
        this.papel = usuario.getPapel();
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPapel() {
        return papel;
    }

    public void setPapel(String papel) {
        this.papel = papel;
    }
}
