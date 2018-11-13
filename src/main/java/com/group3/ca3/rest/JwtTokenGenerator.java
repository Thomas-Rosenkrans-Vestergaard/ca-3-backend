package com.group3.ca3.rest;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.group3.ca3.data.entities.User;

import java.util.Date;

public class JwtTokenGenerator {


    private final BasicJwtSecret secret;


    public JwtTokenGenerator(BasicJwtSecret secret) {
        this.secret = secret;
    }

    public String generateToken(User user){
        Algorithm sha512 = Algorithm.HMAC512(secret.getValue());
        JWTCreator.Builder builder = JWT.create().withClaim("date", new Date()).withClaim("id", user.getId());
        return builder.sign(sha512);
    }

}
