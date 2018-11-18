package com.group3.ca3.logic.facade;

import com.group3.ca3.data.entities.User;
import com.group3.ca3.data.repositories.JpaUserRepository;
import com.group3.ca3.logic.jwt.*;
import com.group3.ca3.rest.JpaConnection;
import org.mindrot.jbcrypt.BCrypt;

import javax.ws.rs.NotAuthorizedException;
import java.io.File;

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

    private static final String           AUTHENTICATION_SCHEME = "Bearer";
    private static final JwtSecret        jwtSecret;
    private static       JwtTokenUnpacker jwtTokenUnpacker;

    static {
        try {
            jwtSecret = new FileJwtSecret(new File("jwt.secret"), 512 / 8);
            jwtTokenUnpacker = new JwtTokenUnpacker(jwtSecret);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String parseToken(String authHeader)
    {
        String token = authHeader.substring(AUTHENTICATION_SCHEME.length()).trim();
        return token;
    }

    private boolean isTokenBasedAuthentication(String authHeader)
    {
        return authHeader != null && authHeader.toLowerCase().startsWith(AUTHENTICATION_SCHEME.toLowerCase() + " ");
    }

    public User authorizeUser(String token)
    {
        try {
            AuthenticationContext authenticationContext = jwtTokenUnpacker.unpack(parseToken(token));
            if (authenticationContext.getRole() == Role.USER) {
                return jpaRepository.getById(authenticationContext.getId());
            }
        } catch (Exception e) {
            throw new NotAuthorizedException("Error during authentication.");
        }

        throw new NotAuthorizedException("You must be user.");
    }

    public User authorizeAdmin(String token)
    {
        try {
            AuthenticationContext authenticationContext = jwtTokenUnpacker.unpack(parseToken(token));
            if (authenticationContext.getRole() == Role.ADMIN) {
                return jpaRepository.getById(authenticationContext.getId());
            }
        } catch (Exception e) {
            throw new NotAuthorizedException("Error during authentication.");
        }

        throw new NotAuthorizedException("You must be admin.");
    }

    private Boolean checkPassword(String password, String hashedPassword)
    {
        return BCrypt.checkpw(password, hashedPassword);
    }

    public String generateAuthenticationToken(User user)
    {
        JwtTokenGenerator generator = new JwtTokenGenerator(jwtSecret);
        return generator.generateToken(user);

    }
}
