package com.group3.ca3.logic.authentication;

import com.group3.ca3.data.entities.User;
import com.group3.ca3.logic.jwt.AuthenticationContext;
import com.group3.ca3.data.repositories.JpaUserRepository;
import com.group3.ca3.rest.JpaConnection;
import org.mindrot.jbcrypt.BCrypt;

public class AuthenticationFacade
{


    private String timingHash = "$2a$10$TcspcbNbrPac2XnfEBdgVeUfhxkUDZ9hvls5jcRAqb6EODbkAKnUy";

    private JpaUserRepository jpaRepository = new JpaUserRepository(JpaConnection.create());


    public AuthenticationContext authenticateUser(String email, String password)
    {
        User user = jpaRepository.getByEmail(email);

        boolean checkHash = checkPassword(password, user == null ? timingHash : user.getPasswordHash());

        if (user != null && checkHash) {
            return new AuthenticationContext(user.getRole(), user.getId());

        }
        return null;
    }


    private Boolean checkPassword(String password, String hashedPassword)
    {
        return BCrypt.checkpw(password, hashedPassword);
    }
}
