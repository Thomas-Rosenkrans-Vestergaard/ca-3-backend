package com.group3.ca3.logic.facade;

import com.group3.ca3.data.entities.User;
import com.group3.ca3.data.repositories.JpaUserRepository;
import com.group3.ca3.logic.jwt.AuthenticationContext;
import com.group3.ca3.logic.jwt.JwtTokenGenerator;
import com.group3.ca3.logic.jwt.JwtTokenUnpacker;
import com.group3.ca3.logic.jwt.Role;
import com.group3.ca3.rest.JpaConnection;
import org.mindrot.jbcrypt.BCrypt;

import javax.ws.rs.NotAuthorizedException;

public class AuthenticationFacade
{


    private String timingHash = "$2a$10$TcspcbNbrPac2XnfEBdgVeUfhxkUDZ9hvls5jcRAqb6EODbkAKnUy";

    private       JpaUserRepository jpaRepository = new JpaUserRepository(JpaConnection.create());
    private final JwtTokenUnpacker  jwtTokenUnpacker;
    private final JwtTokenGenerator jwtTokenGenerator;

    public AuthenticationFacade(JwtTokenUnpacker jwtTokenUnpacker, JwtTokenGenerator jwtTokenGenerator)
    {
        this.jwtTokenUnpacker = jwtTokenUnpacker;
        this.jwtTokenGenerator = jwtTokenGenerator;
    }

    public User authenticateUser(String email, String password)
    {
        User user = jpaRepository.getByEmail(email);

        boolean checkHash = checkPassword(password, user == null ? timingHash : user.getPasswordHash());

        if (user != null && checkHash) {
            return user;
        }

        return null;
    }

    private static final String AUTHENTICATION_SCHEME = "Bearer";

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
        return jwtTokenGenerator.generateToken(user);
    }
}
