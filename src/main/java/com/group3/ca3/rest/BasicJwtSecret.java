package com.group3.ca3.rest;

import java.security.SecureRandom;
import java.util.Base64;

public class BasicJwtSecret implements JwtSecret {

    private byte[] value;
    private SecureRandom random = new SecureRandom();

    public BasicJwtSecret(byte[] value) {
        this.value = value;
    }


    public BasicJwtSecret(int bytes) {
        regenerate(bytes);
    }


    @Override
    public byte[] getValue() {
        return value;
    }

    @Override
    public byte[] regenerate(int bytes) {
        byte[] byteArray = new byte[bytes];
        random.nextBytes(byteArray);
        return this.value = Base64.getEncoder().encode(byteArray);
    }
}

