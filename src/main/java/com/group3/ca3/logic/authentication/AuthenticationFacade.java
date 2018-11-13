package com.group3.ca3.logic.authentication;

import com.group3.ca3.data.entities.User;
import com.group3.ca3.logic.jwt.AuthenticationContext;
import com.group3.ca3.repositories.JpaUserRepository;
import com.group3.ca3.repositories.UserRepository;
import com.group3.ca3.rest.JpaConnection;

import javax.persistence.EntityManagerFactory;

public class AuthenticationFacade {


    private static JpaUserRepository repository = new JpaUserRepository(JpaConnection.create());


    public AuthenticationContext authenticateUser(String email, String password){


        User user = repository.getByEmail(email);

        return null;
    }
}
