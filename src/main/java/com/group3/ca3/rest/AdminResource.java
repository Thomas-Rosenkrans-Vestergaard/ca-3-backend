package com.group3.ca3.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.group3.ca3.data.entities.User;
import com.group3.ca3.logic.facade.AuthenticationFacade;
import com.group3.ca3.logic.jwt.JwtTokenGenerator;
import com.group3.ca3.logic.jwt.JwtTokenUnpacker;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("admin")
public class AdminResource
{

    private static Gson                 gson                 = new GsonBuilder().setPrettyPrinting().create();
    private static AuthenticationFacade authenticationFacade = new AuthenticationFacade(
            new JwtTokenUnpacker(SharedSecret.getSecret()),
            new JwtTokenGenerator(SharedSecret.getSecret())
    );

    @GET
    @Produces(APPLICATION_JSON)
    @Path("message")
    public Response getMessage(@HeaderParam("Authorization") String auth)
    {
        User user = authenticationFacade.authorizeAdmin(auth);

        JsonObject o = new JsonObject();
        o.addProperty("message", "This is a secret message for " + user.getName());
        return Response.ok(gson.toJson(o)).build();
    }
}
