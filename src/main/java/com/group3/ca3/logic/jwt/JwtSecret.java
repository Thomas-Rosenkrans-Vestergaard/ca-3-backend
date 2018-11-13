package com.group3.ca3.logic.jwt;

public interface JwtSecret {


    byte[] getValue();


    byte[] regenerate(int bytes);


}
