package com.group3.ca3.rest;

import com.group3.ca3.rest.http.HttpResponse;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Path("film")
public class GhibliResource {
    CreateConnection con = new CreateConnection();

    @GET
    @Path("all")
    public Response getAllMovies() throws IOException {
        HttpResponse response = con.sendGetRequest("https://ghibliapi.herokuapp.com/films", false);
        return Response.status(response.responseCode).entity(response.contents).build();
    }
}
