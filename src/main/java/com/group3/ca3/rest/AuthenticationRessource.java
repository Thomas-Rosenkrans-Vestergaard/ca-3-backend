package com.group3.ca3.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;


@Path("authentication")
public class AuthenticationRessource {


    @GET
    @Path("user/{email}")
    @Produces(APPLICATION_JSON)
     public Response authenticateUser(@PathParam("email")String email){

        return null;
    }
}
