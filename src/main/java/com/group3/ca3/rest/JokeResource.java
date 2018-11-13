package com.group3.ca3.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Path("Joke")
public class JokeResource {
    CreateConnection con = new CreateConnection();

    @GET
    public Response getJoke() throws IOException {
        String response = con.sendGetRequest("https://08ad1pao69.execute-api.us-east-1.amazonaws.com/dev/random_joke", false);
        return Response.ok(response).build();
    }
}
