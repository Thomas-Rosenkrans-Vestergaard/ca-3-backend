package com.group3.ca3.logic.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.group3.ca3.logic.jwt.BasicJwtSecret;
import com.group3.ca3.logic.jwt.Role;
import com.group3.ca3.logic.jwt.AuthenticationContext;

public class JwtTokenUnpacker {

    private final FileJwtSecret secret;

    public JwtTokenUnpacker(FileJwtSecret secret) {
        this.secret = secret;
    }


    public AuthenticationContext unpack(String token) {

        Algorithm sha512 = Algorithm.HMAC512(secret.getValue());
        DecodedJWT decodedJWT = JWT.require(sha512)
                .build()
                .verify(token);

        Long id =  decodedJWT.getClaim("userId").asLong();
        Role role = decodedJWT.getClaim("role").as(Role.class);


        return new AuthenticationContext(role, id);

    }
}

