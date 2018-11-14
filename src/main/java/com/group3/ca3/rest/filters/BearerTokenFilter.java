package com.group3.ca3.rest.filters;

//lavet af OAuath


import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.ext.Provider;
import java.io.IOException;


@Provider
@PreMatching
public class BearerTokenFilter implements ContainerRequestFilter
{

    //også kendt som bearer
    private static final String AUTHENTICATION_SCHEME = "Bearer";

    public void filter(ContainerRequestContext ctx) throws IOException
    {

        String authHeader = ctx.getHeaderString(HttpHeaders.AUTHORIZATION);

        if (!isTokenBasedAuthentication(authHeader)) {
            throw new NotAuthorizedException("Bearer");
        }

        String token = parseToken(authHeader);
        if (!verifyToken(token)) {
            throw new NotAuthorizedException("Bearer error=\"invalid_token\"");
        }

    }

    private String parseToken(String authHeader)
    {
        String token = authHeader.substring(AUTHENTICATION_SCHEME.length()).trim();
        return token;
    }

    private boolean isTokenBasedAuthentication(String authHeader)
    {
        return authHeader != null && authHeader.toLowerCase().startsWith(AUTHENTICATION_SCHEME.toLowerCase() + " ");
    }

    private boolean verifyToken(String token)
    {
        return false;
    }
}