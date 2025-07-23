package com.eletronicos.service;

import com.eletronicos.bo.UsuarioBO;
import com.eletronicos.model.Usuario;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class UsuarioService {

    @Inject
    EntityManager em;

    @Inject
    UsuarioBO usuarioBO;

    public List<Usuario> listarTodos() {
        return Usuario.listAll();
    }

    public Optional<Usuario> buscarPorEmail(String email) {
        return Usuario.find("email", email).firstResultOptional();
    }

    @Transactional
    public void cadastrar(Usuario usuario) {
        // --- CORREÇÃO AQUI ---
        // 1. Delega a validação para o BO
        usuarioBO.validarNovoUsuario(usuario);
        
        // 2. Delega a criptografia para o BO
        String senhaCriptografada = usuarioBO.criptografarSenha(usuario.getHashSenha());
        usuario.setHashSenha(senhaCriptografada);
        
        // 3. O Service agora só se preocupa com a persistência
        em.persist(usuario);
    }

    @Transactional
    public Optional<Usuario> atualizarPerfil(String email, Usuario usuario) {
        Optional<Usuario> usuarioOpt = buscarPorEmail(email);
        
        if (usuarioOpt.isPresent()) {
            Usuario usuarioDb = usuarioOpt.get();
            
            usuarioDb.setNome(usuario.getNome());
            usuarioDb.setTelefone(usuario.getTelefone());
            // ... outros campos ...
            
            // --- CORREÇÃO AQUI ---
            if (usuario.getHashSenha() != null && !usuario.getHashSenha().isEmpty()) {
                String senhaCriptografada = usuarioBO.criptografarSenha(usuario.getHashSenha());
                usuarioDb.setHashSenha(senhaCriptografada);
            }
            
            return Optional.of(usuarioDb);
        }
        
        return Optional.empty();
    }
}
