package com.eletronicos.service;

import com.eletronicos.model.Categoria;
import com.eletronicos.model.Produto;
import com.eletronicos.model.Usuario;
import org.mindrot.jbcrypt.BCrypt;
import io.quarkus.runtime.StartupEvent;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.math.BigDecimal;

@ApplicationScoped
public class DataInitializationService {

    @Inject
    EntityManager em;

    @Transactional
    public void onStart(@Observes StartupEvent ev) {
        // Apenas executa se não houver utilizadores, para evitar duplicados
        if (Usuario.count() > 0) {
            return;
        }

        System.out.println("Criando dados iniciais...");

        // --- ADICIONADO: Criar categorias ---
        Categoria smartphones = new Categoria();
        smartphones.setNome("Smartphones");
        em.persist(smartphones);

        Categoria notebooks = new Categoria();
        notebooks.setNome("Notebooks");
        em.persist(notebooks);

        // --- ADICIONADO: Criar produtos ---
        Produto produto1 = new Produto();
        produto1.setNome("Smartphone Galaxy S23");
        produto1.setDescricao("O mais recente smartphone Samsung com câmera incrível.");
        produto1.setPreco(new BigDecimal("4999.99"));
        produto1.setEstoque(50);
        produto1.setImagem("https://placehold.co/400x400/EFEFEF/AAAAAA&text=S23");
        produto1.setMarca("Samsung");
        produto1.setDestaque(true);
        produto1.setCategoria(smartphones);
        em.persist(produto1);

        Produto produto2 = new Produto();
        produto2.setNome("MacBook Pro M2");
        produto2.setDescricao("Notebook potente com o novo chip M2 da Apple.");
        produto2.setPreco(new BigDecimal("9999.99"));
        produto2.setEstoque(25);
        produto2.setImagem("https://placehold.co/400x400/EFEFEF/AAAAAA&text=MacBook");
        produto2.setDestaque(true);
        produto2.setCategoria(notebooks);
        em.persist(produto2);

        // --- Utilizadores de teste (já existentes) ---
        Usuario admin = new Usuario();
        admin.setEmail("admin@eletrostore.com");
        admin.setNome("Administrador");
        admin.setPapel("admin");
        admin.setSenha(BCrypt.hashpw("admin123", BCrypt.gensalt()));
        em.persist(admin);

        Usuario usuario = new Usuario();
        usuario.setEmail("cliente@exemplo.com");
        usuario.setNome("Cliente Exemplo");
        usuario.setPapel("usuario");
        usuario.setSenha(BCrypt.hashpw("123456", BCrypt.gensalt()));
        em.persist(usuario);

        System.out.println("Dados iniciais criados com sucesso!");
    }
}
