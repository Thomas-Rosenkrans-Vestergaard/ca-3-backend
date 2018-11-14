package com.group3.ca3.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.group3.ca3.data.entities.User;
import com.group3.ca3.logic.facade.AuthenticationFacade;
import com.group3.ca3.rest.filters.Secured;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import static com.google.common.net.HttpHeaders.AUTHORIZATION;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Transactional
@Path("authentication")
public class AuthenticationResource {

    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Inject
    private AuthenticationFacade authenticationFacade;


    @Secured
    @POST
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public Response authenticateUser(String json) {

        try {

            RecieveAuthenticatedUser authenticatedUser = gson.fromJson(json, RecieveAuthenticatedUser.class);
            User user = authenticationFacade.authenticateUser(authenticatedUser.email, authenticatedUser.password);
            String token = authenticationFacade.generateAuthenticationToken(user);

            return Response.ok(gson.toJson(user)).header(AUTHORIZATION, "Bearer " + token).build();

        } catch (Exception e) {
            Response.status(Response.Status.UNAUTHORIZED).build();
            System.err.print("det fuckede op, på grund af Thomas har ødelagt koden");
            e.printStackTrace();
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }


    private class RecieveAuthenticatedUser{
        String email;
        String password;

    }
}





