package com.eletronicos.filter;

import com.eletronicos.service.TokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Provider
public class AuthenticationFilter implements ContainerRequestFilter {

    @Inject
    TokenService tokenService;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
<<<<<<< HEAD
        // Rotas que não precisam de autenticação
        String path = requestContext.getUriInfo().getPath();
        if (path.startsWith("/api/auth/login") || path.startsWith("/api/usuarios/cadastro")) {
=======
        String path = requestContext.getUriInfo().getPath();
        String method = requestContext.getMethod();

        // --- MUDANÇA PRINCIPAL AQUI ---
        // Permite o acesso público a rotas específicas
        boolean isPublicPath = path.startsWith("/api/auth/login") || 
                               path.startsWith("/api/usuarios/cadastro") ||
                               (path.startsWith("/api/produtos") && method.equals("GET")) ||
                               (path.startsWith("/api/categorias") && method.equals("GET")) ||
                               path.startsWith("/api/carrinho/calcular"); // <-- ROTA DO CARRINHO ADICIONADA

        if (isPublicPath) {
>>>>>>> 57e4a65... refactor(pedido): Implementa BO e DTOs para a entidade Pedido
            return; // Não faz nada, permite a passagem
        }

        // Pega o cabeçalho de autorização
        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        // Verifica se o cabeçalho existe e se está no formato correto ("Bearer [token]")
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            abortWithUnauthorized(requestContext);
            return;
        }

        // Extrai o token
        String token = authorizationHeader.substring("Bearer ".length()).trim();

        try {
            // Valida o token usando o nosso serviço
            Jws<Claims> claims = tokenService.validateToken(token);
            
            // Se o token for válido, criamos um SecurityContext com as informações do utilizador
            final SecurityContext currentSecurityContext = requestContext.getSecurityContext();
            requestContext.setSecurityContext(new SecurityContext() {
                @Override
                public Principal getUserPrincipal() {
<<<<<<< HEAD
                    return () -> claims.getBody().getSubject(); // O "subject" é o e-mail do utilizador
=======
                    return () -> claims.getBody().getSubject();
>>>>>>> 57e4a65... refactor(pedido): Implementa BO e DTOs para a entidade Pedido
                }

                @Override
                @SuppressWarnings("unchecked")
                public boolean isUserInRole(String role) {
                    List<String> roles = claims.getBody().get("roles", List.class);
                    return roles != null && roles.contains(role);
                }

                @Override
                public boolean isSecure() {
                    return currentSecurityContext.isSecure();
                }

                @Override
                public String getAuthenticationScheme() {
                    return "Bearer";
                }
            });

        } catch (Exception e) {
<<<<<<< HEAD
            // Se o token for inválido ou expirado, bloqueia a requisição
=======
>>>>>>> 57e4a65... refactor(pedido): Implementa BO e DTOs para a entidade Pedido
            abortWithUnauthorized(requestContext);
        }
    }

    private void abortWithUnauthorized(ContainerRequestContext requestContext) {
        requestContext.abortWith(
            Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Acesso não autorizado. Por favor, faça o login.")
                    .type(MediaType.APPLICATION_JSON)
                    .build()
        );
    }
}
