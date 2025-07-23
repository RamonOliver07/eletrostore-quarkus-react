package com.eletronicos.filter;

import java.io.IOException;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType; // <-- IMPORT ADICIONADO
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import com.eletronicos.service.TokenService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

@Provider
public class AuthenticationFilter implements ContainerRequestFilter {

	@Inject
	TokenService tokenService;

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		// Rotas que não precisam de autenticação
		String path = requestContext.getUriInfo().getPath();
		if (path.startsWith("/api/auth/login") || path.startsWith("/api/usuarios/cadastro")) {
			return; // Não faz nada, permite a passagem
		}

		// Pega o cabeçalho de autorização
		String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

		// Verifica se o cabeçalho existe e se está no formato correto ("Bearer
		// [token]")
		if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
			abortWithUnauthorized(requestContext);
			return;
		}

		// Extrai o token
		String token = authorizationHeader.substring("Bearer ".length()).trim();

		try {
			// Valida o token usando o nosso serviço
			Jws<Claims> claims = tokenService.validateToken(token);
			// Se o token for válido, a requisição continua.
			// Poderíamos adicionar o utilizador ao contexto de segurança aqui se
			// necessário.
		} catch (Exception e) {
			// Se o token for inválido ou expirado, bloqueia a requisição
			abortWithUnauthorized(requestContext);
		}
	}

	private void abortWithUnauthorized(ContainerRequestContext requestContext) {
		requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
				.entity("Acesso não autorizado. Por favor, faça o login.").type(MediaType.APPLICATION_JSON) // Esta
																											// linha
																											// agora
																											// funciona
				.build());
	}
}
