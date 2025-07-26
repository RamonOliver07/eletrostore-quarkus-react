package com.eletronicos.service;

import com.eletronicos.bo.PedidoBO;
import com.eletronicos.model.ItemPedido;
import com.eletronicos.model.Pedido;
import com.eletronicos.formdto.PedidoFormDTO;
import com.eletronicos.model.StatusPedido;
import com.eletronicos.model.Usuario;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import javax.ws.rs.WebApplicationException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class PedidoService {

    @Inject
    EntityManager em;

    @Inject
    PedidoBO pedidoBO;

    @Transactional
    public List<Pedido> listarPorUsuario(String email) {
        Usuario usuario = Usuario.find("email", email).firstResult();
        if (usuario == null) {
            return Collections.emptyList();
        }
        // --- CORREÇÃO AQUI: Adicionado "LEFT JOIN FETCH p.itens" para carregar os itens ---
        return em.createQuery("SELECT p FROM Pedido p LEFT JOIN FETCH p.itens WHERE p.usuario.id = :userId ORDER BY p.dataPedido DESC", Pedido.class)
                 .setParameter("userId", usuario.id)
                 .getResultList();
    }
    
    @Transactional
    public Optional<Pedido> buscarPorId(Long id) {
        // Adicionando JOIN FETCH aqui também para consistência
        try {
            Pedido pedido = em.createQuery("SELECT p FROM Pedido p LEFT JOIN FETCH p.itens WHERE p.id = :pedidoId", Pedido.class)
                              .setParameter("pedidoId", id)
                              .getSingleResult();
            return Optional.of(pedido);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Transactional
    public Optional<Pedido> buscarPorIdEUsuario(Long id, String email) {
        Usuario usuario = Usuario.find("email", email).firstResult();
        if (usuario == null) {
            return Optional.empty();
        }
        try {
            // --- CORREÇÃO AQUI: Adicionado "LEFT JOIN FETCH p.itens" ---
            Pedido pedido = em.createQuery("SELECT p FROM Pedido p LEFT JOIN FETCH p.itens WHERE p.id = :pedidoId AND p.usuario.id = :userId", Pedido.class)
                              .setParameter("pedidoId", id)
                              .setParameter("userId", usuario.id)
                              .getSingleResult();
            return Optional.of(pedido);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Transactional
    public Pedido criarPedido(PedidoFormDTO dto, String emailUsuario) {
        Usuario usuario = Usuario.<Usuario>find("email", emailUsuario).firstResultOptional()
                .orElseThrow(() -> new WebApplicationException("Utilizador não encontrado", 404));

        Pedido pedido = pedidoBO.construirPedido(dto, usuario);

        em.persist(pedido);

        for (ItemPedido item : pedido.getItens()) {
            em.persist(item);
        }
        
        return pedido;
    }

    @Transactional
    public Optional<Pedido> atualizarStatus(Long id, StatusPedido novoStatus) {
        Optional<Pedido> pedidoOpt = Pedido.findByIdOptional(id);
        
        if (pedidoOpt.isPresent()) {
            Pedido pedido = pedidoOpt.get();
            pedido.setStatus(novoStatus);
            return Optional.of(pedido);
        }
        
        return Optional.empty();
    }
}
