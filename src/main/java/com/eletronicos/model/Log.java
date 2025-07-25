package com.eletronicos.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "auditoria_logs") 
public class Log extends PanacheEntity {

    private LocalDateTime timestamp;
    private String emailUsuario;
    private String acao;
    private String detalhes;

    // Construtor vazio
    public Log() {
    }

    // Construtor para facilitar a criação
    public Log(String emailUsuario, String acao, String detalhes) {
        this.timestamp = LocalDateTime.now();
        this.emailUsuario = emailUsuario;
        this.acao = acao;
        this.detalhes = detalhes;
    }

    // Getters e Setters
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getEmailUsuario() {
        return emailUsuario;
    }

    public void setEmailUsuario(String emailUsuario) {
        this.emailUsuario = emailUsuario;
    }

    public String getAcao() {
        return acao;
    }

    public void setAcao(String acao) {
        this.acao = acao;
    }

    public String getDetalhes() {
        return detalhes;
    }

    public void setDetalhes(String detalhes) {
        this.detalhes = detalhes;
    }
}
