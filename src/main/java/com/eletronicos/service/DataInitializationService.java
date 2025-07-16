package com.eletronicos.service;

import com.eletronicos.model.Categoria;
import com.eletronicos.model.Produto;
import com.eletronicos.model.Usuario;
import io.quarkus.runtime.StartupEvent;
import org.mindrot.jbcrypt.BCrypt; // Import da biblioteca correta

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.transaction.Transactional;
import java.math.BigDecimal;

@ApplicationScoped
public class DataInitializationService {

    @Transactional
    public void onStart(@Observes StartupEvent ev) {
        if (Usuario.count() > 0) {
            return; 
        }

        System.out.println("Criando dados iniciais com senhas criptografadas corretamente...");

        // Criar usuário administrador com a criptografia jbcrypt
        Usuario admin = new Usuario();
        admin.setEmail("admin@eletrostore.com");
        admin.setNome("Administrador");
        admin.setPapel("admin");
        admin.setSenha(BCrypt.hashpw("admin123", BCrypt.gensalt())); // Usando jbcrypt
        admin.persist();

        // Criar usuário comum com a criptografia jbcrypt
        Usuario usuario = new Usuario();
        usuario.setEmail("cliente@exemplo.com");
        usuario.setNome("Cliente Exemplo");
        usuario.setPapel("usuario");
        usuario.setSenha(BCrypt.hashpw("123456", BCrypt.gensalt())); // Usando jbcrypt
        usuario.persist();
        
        // ... (o resto do seu código que cria categorias e produtos pode vir aqui)
        
        System.out.println("Dados iniciais criados com sucesso!");
    }
}
