package com.group3.ca3.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.group3.ca3.data.entities.User;
import com.group3.ca3.logic.facade.AuthenticationFacade;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Transactional
@Path("authentication")
public class AuthenticationRessource {


    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Inject
    private AuthenticationFacade authenticationFacade;

    @GET
    @Path("login")
    @Produces(APPLICATION_JSON)
    public Response authenticateUser(@FormParam("email") String email, @FormParam("password") String password) {

        try {

            User user = authenticationFacade.authenticateUser(email, password);

            String token = authenticationFacade.generateAuthenticationToken(user);

           return Response.ok(gson.toJson(user)).header(AUTHORIZATION, "Bearer " + token).build();

        } catch (Exception e) {
            Response.status(Response.Status.UNAUTHORIZED).build();
            System.err.print("det fuckede op, på grund af Thomas har ødelagt koden");
            e.printStackTrace();
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }
}





