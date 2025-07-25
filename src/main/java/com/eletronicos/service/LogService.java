package com.eletronicos.service;

import com.eletronicos.model.Log;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@ApplicationScoped
public class LogService {

    @Inject
    EntityManager em;

    /**
     * Regista uma ação de auditoria na base de dados.
     * Este método é chamado de forma assíncrona para não atrasar a resposta ao utilizador.
     * @param emailUsuario O e-mail do utilizador que executou a ação.
     * @param acao A descrição da ação (ex: "LOGIN", "CRIAÇÃO_PRODUTO").
     * @param detalhes Informações adicionais sobre a ação (ex: "Produto ID: 123").
     */
    @Transactional
    public void registrarAcao(String emailUsuario, String acao, String detalhes) {
        Log novoLog = new Log(emailUsuario, acao, detalhes);
        em.persist(novoLog);
    }
}
