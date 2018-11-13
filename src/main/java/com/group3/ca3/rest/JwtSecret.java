package com.group3.ca3.rest;

public interface JwtSecret {


    byte[] getValue();


    byte[] regenerate(int bytes);


}
