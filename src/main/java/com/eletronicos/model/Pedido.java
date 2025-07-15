package com.eletronicos.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Pedido extends PanacheEntity {

    @NotNull
    private LocalDateTime dataPedido = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    private StatusPedido status = StatusPedido.PENDENTE;

    private BigDecimal valorTotal = BigDecimal.ZERO;
    
    private BigDecimal valorFrete = BigDecimal.ZERO;
    
    private String metodoPagamento;
    
    private String numeroRastreio;

    @ManyToOne
    private Usuario usuario;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemPedido> itens = new ArrayList<>();

    // Getters e Setters
    public LocalDateTime getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(LocalDateTime dataPedido) {
        this.dataPedido = dataPedido;
    }

    public StatusPedido getStatus() {
        return status;
    }

    public void setStatus(StatusPedido status) {
        this.status = status;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }
    
    public BigDecimal getValorFrete() {
        return valorFrete;
    }
    
    public void setValorFrete(BigDecimal valorFrete) {
        this.valorFrete = valorFrete;
    }
    
    public String getMetodoPagamento() {
        return metodoPagamento;
    }
    
    public void setMetodoPagamento(String metodoPagamento) {
        this.metodoPagamento = metodoPagamento;
    }
    
    public String getNumeroRastreio() {
        return numeroRastreio;
    }
    
    public void setNumeroRastreio(String numeroRastreio) {
        this.numeroRastreio = numeroRastreio;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<ItemPedido> getItens() {
        return itens;
    }

    public void setItens(List<ItemPedido> itens) {
        this.itens = itens;
    }
    
    // Métodos auxiliares
    public void calcularTotal() {
        this.valorTotal = itens.stream()
            .map(ItemPedido::getSubtotal)
            .reduce(BigDecimal.ZERO, BigDecimal::add)
            .add(this.valorFrete);
    }
    
    public void adicionarItem(ItemPedido item) {
        itens.add(item);
        item.setPedido(this);
        calcularTotal();
    }
    
    public void removerItem(ItemPedido item) {
        itens.remove(item);
        item.setPedido(null);
        calcularTotal();
    }
}