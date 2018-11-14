package com.group3.ca3.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Path("dogs")
public class DogImageResource {

    CreateConnection con = new CreateConnection();

    @GET
    @Path("{breed}")
    public Response getDogById(@PathParam("breed") String breed) throws IOException {
        String response = con.sendGetRequest("https://dog.ceo/api/breed/" + breed + "/images", false);
        return Response.ok(response).build();
    }


}
