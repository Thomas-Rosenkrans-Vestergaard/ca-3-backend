package com.group3.ca3.rest;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JwtTokenUnpacker {

    private final BasicJwtSecret secret;


    public JwtTokenUnpacker(BasicJwtSecret secret) {
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

