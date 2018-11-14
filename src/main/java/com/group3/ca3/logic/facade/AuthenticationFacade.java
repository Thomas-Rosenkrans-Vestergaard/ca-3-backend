package com.group3.ca3.logic.facade;

import com.group3.ca3.data.entities.User;
import com.group3.ca3.data.repositories.JpaUserRepository;
import com.group3.ca3.logic.jwt.BasicJwtSecret;
import com.group3.ca3.logic.jwt.JwtTokenGenerator;
import com.group3.ca3.rest.JpaConnection;
import org.mindrot.jbcrypt.BCrypt;

public class AuthenticationFacade
{


    private String timingHash = "$2a$10$TcspcbNbrPac2XnfEBdgVeUfhxkUDZ9hvls5jcRAqb6EODbkAKnUy";

    private JpaUserRepository jpaRepository = new JpaUserRepository(JpaConnection.create());


    public User authenticateUser(String email, String password)
    {
        User user = jpaRepository.getByEmail(email);

        boolean checkHash = checkPassword(password, user == null ? timingHash : user.getPasswordHash());

        if (user != null && checkHash) {
            return user;

        }
        return null;
    }

    private Boolean checkPassword(String password, String hashedPassword)
    {
        return BCrypt.checkpw(password, hashedPassword);
    }

    public String generateAuthenticationToken(User user)
    {
        BasicJwtSecret    jwtSecret = new BasicJwtSecret(512 / 8);
        JwtTokenGenerator generator = new JwtTokenGenerator(jwtSecret);
        return generator.generateToken(user);

    }
}
