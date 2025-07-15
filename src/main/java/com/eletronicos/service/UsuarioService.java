package com.eletronicos.service;

import com.eletronicos.model.Usuario;
import io.quarkus.elytron.security.common.BcryptUtil;
import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class UsuarioService {

    public List<Usuario> listarTodos() {
        return Usuario.listAll();
    }

    public Optional<Usuario> buscarPorId(Long id) {
        return Optional.ofNullable(Usuario.findById(id));
    }

    public Optional<Usuario> buscarPorEmail(String email) {
        return Optional.ofNullable(Usuario.find("email", email).firstResult());
    }

    public void cadastrar(Usuario usuario) {
        // Verificar se o e-mail já está cadastrado
        if (buscarPorEmail(usuario.getEmail()).isPresent()) {
            throw new RuntimeException("E-mail já cadastrado");
        }
        
        // Criptografar a senha
        usuario.setSenha(BcryptUtil.bcryptHash(usuario.getSenha()));
        
        // Por padrão, papel é "usuario"
        if (usuario.getPapel() == null) {
            usuario.setPapel("usuario");
        }
        
        usuario.persist();
    }

    public Optional<Usuario> atualizarPerfil(String email, Usuario usuario) {
        Optional<Usuario> usuarioOpt = buscarPorEmail(email);
        
        if (usuarioOpt.isPresent()) {
            Usuario usuarioDb = usuarioOpt.get();
            
            // Atualizar campos
            usuarioDb.setNome(usuario.getNome());
            usuarioDb.setTelefone(usuario.getTelefone());
            usuarioDb.setEndereco(usuario.getEndereco());
            usuarioDb.setCidade(usuario.getCidade());
            usuarioDb.setEstado(usuario.getEstado());
            usuarioDb.setCep(usuario.getCep());
            
            // Se senha foi fornecida, atualizar
            if (usuario.getSenha() != null && !usuario.getSenha().isEmpty()) {
                usuarioDb.setSenha(BcryptUtil.bcryptHash(usuario.getSenha()));
            }
            
            usuarioDb.persist();
            return Optional.of(usuarioDb);
        }
        
        return Optional.empty();
    }
}