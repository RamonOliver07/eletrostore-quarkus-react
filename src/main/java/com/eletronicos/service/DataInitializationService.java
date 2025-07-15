package com.eletronicos.service;

import com.eletronicos.model.Categoria;
import com.eletronicos.model.Produto;
import com.eletronicos.model.Usuario;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.runtime.StartupEvent;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.transaction.Transactional;
import java.math.BigDecimal;

@ApplicationScoped
public class DataInitializationService {

    @Transactional
    public void onStart(@Observes StartupEvent ev) {
        // Verificar se já existem dados
        if (Categoria.count() > 0) {
            return; // Dados já foram inicializados
        }

        // Criar categorias
        Categoria smartphones = new Categoria();
        smartphones.setNome("Smartphones");
        smartphones.setDescricao("Celulares e Smartphones");
        smartphones.setIcone("fas fa-mobile-alt");
        smartphones.persist();

        Categoria notebooks = new Categoria();
        notebooks.setNome("Notebooks");
        notebooks.setDescricao("Notebooks e Laptops");
        notebooks.setIcone("fas fa-laptop");
        notebooks.persist();

        Categoria tablets = new Categoria();
        tablets.setNome("Tablets");
        tablets.setDescricao("Tablets e iPads");
        tablets.setIcone("fas fa-tablet-alt");
        tablets.persist();

        Categoria acessorios = new Categoria();
        acessorios.setNome("Acessórios");
        acessorios.setDescricao("Acessórios e Periféricos");
        acessorios.setIcone("fas fa-headphones");
        acessorios.persist();

        // Criar produtos
        Produto produto1 = new Produto();
        produto1.setNome("Smartphone Galaxy S23");
        produto1.setDescricao("O mais recente smartphone Samsung com câmera incrível de 200MP");
        produto1.setPreco(new BigDecimal("4999.99"));
        produto1.setEstoque(50);
        produto1.setImagem("https://images.pexels.com/photos/47261/pexels-photo-47261.jpeg");
        produto1.setMarca("Samsung");
        produto1.setModelo("Galaxy S23");
        produto1.setDestaque(true);
        produto1.setCategoria(smartphones);
        produto1.persist();

        Produto produto2 = new Produto();
        produto2.setNome("MacBook Pro M2");
        produto2.setDescricao("Notebook potente com o novo chip M2 da Apple");
        produto2.setPreco(new BigDecimal("9999.99"));
        produto2.setEstoque(25);
        produto2.setImagem("https://images.pexels.com/photos/812264/pexels-photo-812264.jpeg");
        produto2.setMarca("Apple");
        produto2.setModelo("MacBook Pro M2");
        produto2.setDestaque(true);
        produto2.setCategoria(notebooks);
        produto2.persist();

        Produto produto3 = new Produto();
        produto3.setNome("iPad Pro");
        produto3.setDescricao("Tablet profissional da Apple com tela Liquid Retina");
        produto3.setPreco(new BigDecimal("6999.99"));
        produto3.setEstoque(30);
        produto3.setImagem("https://images.pexels.com/photos/1334597/pexels-photo-1334597.jpeg");
        produto3.setMarca("Apple");
        produto3.setModelo("iPad Pro");
        produto3.setDestaque(false);
        produto3.setCategoria(tablets);
        produto3.persist();

        Produto produto4 = new Produto();
        produto4.setNome("Fones Bluetooth JBL");
        produto4.setDescricao("Fones sem fio com qualidade excepcional e cancelamento de ruído");
        produto4.setPreco(new BigDecimal("299.99"));
        produto4.setEstoque(100);
        produto4.setImagem("https://images.pexels.com/photos/3394665/pexels-photo-3394665.jpeg");
        produto4.setMarca("JBL");
        produto4.setModelo("Tune 760NC");
        produto4.setDestaque(true);
        produto4.setCategoria(acessorios);
        produto4.persist();

        Produto produto5 = new Produto();
        produto5.setNome("iPhone 14 Pro");
        produto5.setDescricao("iPhone com chip A16 Bionic e câmera de 48MP");
        produto5.setPreco(new BigDecimal("7999.99"));
        produto5.setEstoque(40);
        produto5.setImagem("https://images.pexels.com/photos/9555008/pexels-photo-9555008.jpeg");
        produto5.setMarca("Apple");
        produto5.setModelo("iPhone 14 Pro");
        produto5.setDestaque(true);
        produto5.setCategoria(smartphones);
        produto5.persist();

        Produto produto6 = new Produto();
        produto6.setNome("Dell XPS 13");
        produto6.setDescricao("Ultrabook premium com tela InfinityEdge");
        produto6.setPreco(new BigDecimal("5999.99"));
        produto6.setEstoque(20);
        produto6.setImagem("https://images.pexels.com/photos/205421/pexels-photo-205421.jpeg");
        produto6.setMarca("Dell");
        produto6.setModelo("XPS 13");
        produto6.setDestaque(false);
        produto6.setCategoria(notebooks);
        produto6.persist();

        // Criar usuário administrador
        Usuario admin = new Usuario();
        admin.setEmail("admin@eletrostore.com");
        admin.setSenha(BcryptUtil.bcryptHash("admin123"));
        admin.setNome("Administrador");
        admin.setPapel("admin");
        admin.persist();

        // Criar usuário comum
        Usuario usuario = new Usuario();
        usuario.setEmail("cliente@exemplo.com");
        usuario.setSenha(BcryptUtil.bcryptHash("123456"));
        usuario.setNome("Cliente Exemplo");
        usuario.setPapel("usuario");
        usuario.persist();

        System.out.println("Dados iniciais criados com sucesso!");
    }
}