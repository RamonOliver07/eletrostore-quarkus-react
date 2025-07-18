package com.eletronicos.service;

import com.eletronicos.bo.UsuarioBO;
import com.eletronicos.model.Usuario;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * Service para a entidade Usuario.
 * Orquestra as ações, delegando as regras de negócio para o UsuarioBO.
 */
@ApplicationScoped
public class UsuarioService {

    @Inject
    EntityManager em;

    @Inject
    UsuarioBO usuarioBO; // <-- Injeta o nosso novo Business Object

    public List<Usuario> listarTodos() {
        return Usuario.listAll();
    }

    public Optional<Usuario> buscarPorId(Long id) {
        return Optional.ofNullable(Usuario.findById(id));
    }

    public Optional<Usuario> buscarPorEmail(String email) {
        return Usuario.find("email", email).firstResultOptional();
    }

    @Transactional
    public void cadastrar(Usuario usuario) {
        // 1. Delega a validação para o BO
        usuarioBO.validarNovoUsuario(usuario);
        
        // 2. Delega a criptografia para o BO
        String senhaCriptografada = usuarioBO.criptografarSenha(usuario.getSenha());
        usuario.setSenha(senhaCriptografada);
        
        // Define o papel padrão
        if (usuario.getPapel() == null) {
            usuario.setPapel("usuario");
        }
        
        // 3. O Service agora só se preocupa com a persistência
        em.persist(usuario);
    }

    @Transactional
    public Optional<Usuario> atualizarPerfil(String email, Usuario usuario) {
        Optional<Usuario> usuarioOpt = buscarPorEmail(email);
        
        if (usuarioOpt.isPresent()) {
            Usuario usuarioDb = usuarioOpt.get();
            
            // Atualiza os campos
            usuarioDb.setNome(usuario.getNome());
            usuarioDb.setTelefone(usuario.getTelefone());
            usuarioDb.setEndereco(usuario.getEndereco());
            usuarioDb.setCidade(usuario.getCidade());
            usuarioDb.setEstado(usuario.getEstado());
            usuarioDb.setCep(usuario.getCep());
            
            // Se a senha foi fornecida, criptografa usando o BO
            if (usuario.getSenha() != null && !usuario.getSenha().isEmpty()) {
                String senhaCriptografada = usuarioBO.criptografarSenha(usuario.getSenha());
                usuarioDb.setSenha(senhaCriptografada);
            }
            
            em.merge(usuarioDb);
            return Optional.of(usuarioDb);
        }
        
        return Optional.empty();
    }
}
