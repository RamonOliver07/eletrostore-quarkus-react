package com.eletronicos.filter;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.ext.Provider;
import org.jboss.resteasy.reactive.server.ServerRequestFilter;

import static java.util.Objects.nonNull;

@Provider
@Priority(Priorities.AUTHENTICATION - 100)
public class RequestFilter {

    @ServerRequestFilter(preMatching = true)
    public void preMatchingFilter(ContainerRequestContext requestContext) {
        Cookie token = requestContext.getCookies().get("token");
        if (nonNull(token)) {
            requestContext.getHeaders().remove("Authorization");
            requestContext.getHeaders().add("Authorization", "Bearer " + token.getValue());
        }
    }
}
