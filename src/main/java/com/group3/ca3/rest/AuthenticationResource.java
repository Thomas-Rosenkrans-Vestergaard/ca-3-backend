package com.group3.ca3.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.group3.ca3.data.entities.User;
import com.group3.ca3.logic.facade.AuthenticationFacade;
import com.group3.ca3.logic.jwt.JwtTokenGenerator;
import com.group3.ca3.logic.jwt.JwtTokenUnpacker;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("authentication")
public class AuthenticationResource
{

    private static Gson                 gson                 = new GsonBuilder().setPrettyPrinting().create();
    private static AuthenticationFacade authenticationFacade = new AuthenticationFacade(
            new JwtTokenUnpacker(SharedSecret.getSecret()),
            new JwtTokenGenerator(SharedSecret.getSecret())
    );

    @POST
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public Response authenticateUser(String json)
    {
        try {
            ReceivedAuthenticatedUser receivedUser = gson.fromJson(json, ReceivedAuthenticatedUser.class);
            User                      user         = authenticationFacade.authenticateUser(receivedUser.email, receivedUser.password);
            if (user == null)
                return Response.status(404).build();

            String token = authenticationFacade.generateAuthenticationToken(user);

            return Response.ok(gson.toJson(new DTO(user, token))).build();

        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    private class DTO
    {
        public User   user;
        public String token;

        public DTO(User user, String token)
        {
            this.user = user;
            this.token = token;
        }
    }

    private class ReceivedAuthenticatedUser
    {
        String email;
        String password;

    }
}





