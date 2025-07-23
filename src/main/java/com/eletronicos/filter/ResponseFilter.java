package com.eletronicos.filter;

import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.reactive.server.ServerResponseFilter;

import static java.util.Objects.nonNull;

@Provider // Adicione esta anotação para que o Quarkus reconheça a classe
public class ResponseFilter {

    @ServerResponseFilter()
    public void responseFilter(ContainerResponseContext responseContext) {
        String token = responseContext.getHeaderString("token");
        if (nonNull(token)) {
            // Cria um cookie seguro que só pode ser lido pelo servidor (HttpOnly)
            String cookie = "token=" + token + "; Path=/; Max-Age=3600; HttpOnly; Secure; SameSite=Strict";
            responseContext.getHeaders().add("Set-Cookie", cookie);
        }
    }
}
