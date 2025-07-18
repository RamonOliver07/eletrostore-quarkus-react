package com.eletronicos.bo;

import com.eletronicos.model.Usuario;
import org.mindrot.jbcrypt.BCrypt;

import javax.enterprise.context.ApplicationScoped;
import java.util.Optional;

/**
 * BO (Business Object) para a entidade Usuario.
 * Contém todas as regras de negócio relacionadas a usuários.
 */
@ApplicationScoped
public class UsuarioBO {

    /**
     * Valida e processa um novo usuário antes do cadastro.
     * @param usuario O objeto Usuario a ser validado.
     * @throws RuntimeException se uma regra de negócio for violada.
     */
    public void validarNovoUsuario(Usuario usuario) {
        // Regra 1: Validação de campos obrigatórios (a anotação @NotBlank já ajuda)
        if (usuario.getNome() == null || usuario.getNome().isBlank()) {
            throw new IllegalArgumentException("O campo nome é obrigatório.");
        }
        if (usuario.getEmail() == null || usuario.getEmail().isBlank()) {
            throw new IllegalArgumentException("O campo email é obrigatório.");
        }
        if (usuario.getSenha() == null || usuario.getSenha().isBlank()) {
            throw new IllegalArgumentException("O campo senha é obrigatório.");
        }

        // Regra 2: Verificar se o e-mail já está cadastrado
        Optional<Usuario> usuarioExistente = Usuario.find("email", usuario.getEmail()).firstResultOptional();
        if (usuarioExistente.isPresent()) {
            throw new RuntimeException("E-mail já cadastrado");
        }
    }

    /**
     * Criptografa a senha do usuário.
     * @param senhaPura A senha em texto plano.
     * @return A senha criptografada (hash).
     */
    public String criptografarSenha(String senhaPura) {
        return BCrypt.hashpw(senhaPura, BCrypt.gensalt());
    }
}
