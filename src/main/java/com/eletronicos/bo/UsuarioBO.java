package com.eletronicos.bo;

import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;

import org.mindrot.jbcrypt.BCrypt;

import com.eletronicos.model.Usuario;

/**
 * BO (Business Object) para a entidade Usuario. Contém todas as regras de
 * negócio relacionadas a utilizadores.
 */
@ApplicationScoped
public class UsuarioBO {

	/**
	 * Valida e processa um novo utilizador antes do cadastro.
	 * 
	 * @param usuario O objeto Usuario a ser validado.
	 * @throws RuntimeException se uma regra de negócio for violada.
	 */
	public void validarNovoUsuario(Usuario usuario) {
		if (usuario.getNome() == null || usuario.getNome().isBlank()) {
			throw new IllegalArgumentException("O campo nome é obrigatório.");
		}
		if (usuario.getEmail() == null || usuario.getEmail().isBlank()) {
			throw new IllegalArgumentException("O campo email é obrigatório.");
		}
		// --- CORREÇÃO AQUI ---
		if (usuario.getHashSenha() == null || usuario.getHashSenha().isBlank()) {
			throw new IllegalArgumentException("O campo senha é obrigatório.");
		}

		Optional<Usuario> usuarioExistente = Usuario.find("email", usuario.getEmail()).firstResultOptional();
		if (usuarioExistente.isPresent()) {
			throw new RuntimeException("E-mail já cadastrado");
		}
	}

	/**
	 * Criptografa a senha do utilizador.
	 * 
	 * @param senhaPura A senha em texto plano.
	 * @return A senha criptografada (hash).
	 */
	public String criptografarSenha(String senhaPura) {
		return BCrypt.hashpw(senhaPura, BCrypt.gensalt());
	}
}
