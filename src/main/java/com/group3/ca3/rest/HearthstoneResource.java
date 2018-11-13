package com.group3.ca3.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Path("hearthstone")
public class HearthstoneResource {
    CreateConnection con = new CreateConnection();

    @GET
    @Path("all")
    public Response getAllCards() throws IOException {
        con.sendGetRequest("https://omgvamp-hearthstone-v1.p.mashape.com/cards", true);
        return Response.ok().build();
    }

    @GET
    @Path("search/{name}")
    public Response getCardByName(@PathParam("name") String name) throws IOException {
        String response = con.sendGetRequest("https://omgvamp-hearthstone-v1.p.mashape.com/cards/search/"+name, true);
        return Response.ok(response).build();
    }
}
