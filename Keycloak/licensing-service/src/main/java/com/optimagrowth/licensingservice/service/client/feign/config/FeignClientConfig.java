package com.optimagrowth.licensingservice.service.client.feign.config;

import java.security.Principal;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import feign.RequestInterceptor;
import feign.RequestTemplate;

public class FeignClientConfig implements RequestInterceptor {

    @SuppressWarnings("rawtypes")
    protected KeycloakSecurityContext getKeycloakSecurityContext() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        Principal principal = attributes.getRequest().getUserPrincipal();
        if (principal == null) {
            throw new IllegalStateException(
                    "Cannot set authorization header because there is no authenticated principal");
        }

        if (!(principal instanceof KeycloakAuthenticationToken)) {
            throw new IllegalStateException(String.format(
                    "Cannot set authorization header because the principal type %s does not provide the KeycloakPrincipal",
                    principal.getClass()));
        }

        principal = (Principal) ((KeycloakAuthenticationToken) principal).getPrincipal();

        if (!(principal instanceof KeycloakPrincipal)) {
            throw new IllegalStateException(String.format(
                    "Cannot set authorization header because the principal type %s does not provide the KeycloakSecurityContext",
                    principal.getClass()));
        }
        return ((KeycloakPrincipal) principal).getKeycloakSecurityContext();
    }

    @Override
    public void apply(RequestTemplate template) {
        template.header(HttpHeaders.AUTHORIZATION, "Bearer " + this.getKeycloakSecurityContext().getTokenString());
    }

}
