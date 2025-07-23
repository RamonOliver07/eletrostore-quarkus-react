package com.eletronicos.service;

import com.eletronicos.model.Usuario;
import org.mindrot.jbcrypt.BCrypt;
import io.quarkus.runtime.StartupEvent;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@ApplicationScoped
public class DataInitializationService {

    @Inject
    EntityManager em;

    @Transactional
    public void onStart(@Observes StartupEvent ev) {
        if (Usuario.count() > 0) {
            return;
        }

        System.out.println("Criando apenas utilizadores de teste (admin e cliente)...");

        Usuario admin = new Usuario();
        admin.setEmail("admin@eletrostore.com");
        admin.setNome("Administrador");
        admin.setPapel("admin");
        admin.setHashSenha(BCrypt.hashpw("admin123", BCrypt.gensalt())); // <-- CORRIGIDO
        em.persist(admin);

        Usuario usuario = new Usuario();
        usuario.setEmail("cliente@exemplo.com");
        usuario.setNome("Cliente Exemplo");
        usuario.setPapel("usuario");
        usuario.setHashSenha(BCrypt.hashpw("123456", BCrypt.gensalt())); // <-- CORRIGIDO
        em.persist(usuario);

        System.out.println("Utilizadores de teste criados com sucesso!");
    }
}
