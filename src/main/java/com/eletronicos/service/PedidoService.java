package com.eletronicos.service;

import com.eletronicos.bo.PedidoBO;
import com.eletronicos.formdto.PedidoFormDTO;
import com.eletronicos.model.Pedido;
import com.eletronicos.model.StatusPedido;
import com.eletronicos.model.Usuario;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.WebApplicationException;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class PedidoService {

    @Inject
    EntityManager em;

    @Inject
    PedidoBO pedidoBO; // Injeta o Business Object

    public List<Pedido> listarPorUsuario(String email) {
        Usuario usuario = Usuario.find("email", email).firstResult();
        return Pedido.list("usuario", usuario);
    }
    
    public Optional<Pedido> buscarPorId(Long id) {
        return Pedido.findByIdOptional(id);
    }

    public Optional<Pedido> buscarPorIdEUsuario(Long id, String email) {
        Usuario usuario = Usuario.find("email", email).firstResult();
        if (usuario == null) {
            return Optional.empty();
        }
        Pedido pedido = Pedido.find("id = ?1 and usuario = ?2", id, usuario).firstResult();
        return Optional.ofNullable(pedido);
    }

    // --- MÉTODO ADICIONADO QUE ESTAVA EM FALTA ---
    @Transactional
    public Pedido criarPedido(PedidoFormDTO dto, String emailUsuario) {
        // 1. Busca o utilizador
        Usuario usuario = Usuario.<Usuario>find("email", emailUsuario).firstResultOptional()
                .orElseThrow(() -> new WebApplicationException("Utilizador não encontrado", 404));

        // 2. Delega a validação e a construção para o BO
        Pedido pedido = pedidoBO.construirPedido(dto, usuario);

        // 3. O Service agora só se preocupa com a persistência
        em.persist(pedido);
        
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
