package com.group3.ca3.rest;

import com.group3.ca3.logic.jwt.BasicJwtSecret;
import com.group3.ca3.logic.jwt.JwtSecret;

public class SharedSecret
{

    private static JwtSecret secret;
    public static JwtSecret getSecret(){
        if(secret == null)
            secret = new BasicJwtSecret(512 / 8);
        return secret;
    }
}
