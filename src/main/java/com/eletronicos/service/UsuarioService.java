package com.eletronicos.service;

import com.eletronicos.model.Usuario;
import org.mindrot.jbcrypt.BCrypt; // <-- MUDANÇA: Import da nova biblioteca

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject; // <-- ADICIONE ESTE IMPORT
import javax.persistence.EntityManager; // <-- ADICIONE ESTE IMPORT
import javax.transaction.Transactional; // <-- ADICIONE ESTE IMPORT
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class UsuarioService {

    @Inject // <-- ADICIONE ESTA LINHA (Diz ao Quarkus para nos dar o EntityManager)
    EntityManager em;

    public List<Usuario> listarTodos() {
        return Usuario.listAll();
    }

    public Optional<Usuario> buscarPorId(Long id) {
        return Optional.ofNullable(Usuario.findById(id));
    }

    public Optional<Usuario> buscarPorEmail(String email) {
        return Optional.ofNullable(Usuario.find("email", email).firstResult());
    }

    @Transactional // <-- ADICIONE ESTA ANOTAÇÃO
    public void cadastrar(Usuario usuario) {
        if (buscarPorEmail(usuario.getEmail()).isPresent()) {
            throw new RuntimeException("E-mail já cadastrado");
        }

        // Criptografia MANUAL com jbcrypt
        String hash = BCrypt.hashpw(usuario.getSenha(), BCrypt.gensalt());
        usuario.setSenha(hash);

        if (usuario.getPapel() == null) {
            usuario.setPapel("usuario");
        }

        // Usamos o EntityManager para persistir
        em.persist(usuario); 
    }

    @Transactional // <-- ADICIONE ESTA ANOTAÇÃO
    public Optional<Usuario> atualizarPerfil(String email, Usuario usuario) {
        Optional<Usuario> usuarioOpt = buscarPorEmail(email);

        if (usuarioOpt.isPresent()) {
            Usuario usuarioDb = usuarioOpt.get();

            usuarioDb.setNome(usuario.getNome());
            usuarioDb.setTelefone(usuario.getTelefone());
            usuarioDb.setEndereco(usuario.getEndereco());
            usuarioDb.setCidade(usuario.getCidade());
            usuarioDb.setEstado(usuario.getEstado());
            usuarioDb.setCep(usuario.getCep());

            if (usuario.getSenha() != null && !usuario.getSenha().isEmpty()) {
                // Criptografia MANUAL com jbcrypt
                String hash = BCrypt.hashpw(usuario.getSenha(), BCrypt.gensalt());
                usuarioDb.setSenha(hash);
            }

            // Usamos o EntityManager aqui também
            em.merge(usuarioDb); 
            return Optional.of(usuarioDb);
        }

        return Optional.empty();
    }
}