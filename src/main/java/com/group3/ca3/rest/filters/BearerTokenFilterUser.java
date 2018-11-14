package com.group3.ca3.rest.filters;

//lavet af OAuath


import com.group3.ca3.logic.jwt.AuthenticationContext;
import com.group3.ca3.logic.jwt.FileJwtSecret;
import com.group3.ca3.logic.jwt.JwtTokenUnpacker;
import com.group3.ca3.logic.jwt.Role;

import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.ext.Provider;
import java.io.File;
import java.io.IOException;


@Secured
@Provider
@PreMatching
public class BearerTokenFilterUser implements ContainerRequestFilter {

    //også kendt som bearer
    private static final String AUTHENTICATION_SCHEME = "Bearer";
    private static JwtTokenUnpacker jwtTokenUnpacker;

    static {
        try {
            jwtTokenUnpacker = new JwtTokenUnpacker(new FileJwtSecret(new File("jwt.secret"), 512 / 8));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public void filter(ContainerRequestContext ctx) throws IOException {

        String authHeader = ctx.getHeaderString(HttpHeaders.AUTHORIZATION);

        String token = parseToken(authHeader);
        if (!verifyToken(token)) {
            throw new NotAuthorizedException("Bearer error=\"invalid_token\"");
        }

        if (!isTokenBasedAuthentication(authHeader)) {
            throw new NotAuthorizedException("Bearer");
        }



    }

    private String parseToken(String authHeader) {
        String token = authHeader.substring(AUTHENTICATION_SCHEME.length()).trim();
        return token;
    }

    private boolean isTokenBasedAuthentication(String authHeader) {
        return authHeader != null && authHeader.toLowerCase().startsWith(AUTHENTICATION_SCHEME.toLowerCase() + " ");
    }

    private boolean verifyToken(String token) {
        AuthenticationContext authenticationContext = jwtTokenUnpacker.unpack(token);
        if (authenticationContext.getRole() == Role.USER) {
            return true;
        }
        return false;
    }
}