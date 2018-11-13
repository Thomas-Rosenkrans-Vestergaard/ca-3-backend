package rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Path("pokemon")
public class PokemonResource {
    CreateConnection con = new CreateConnection();

    @GET
    @Path("{id}")
    public Response getPokemonById(@PathParam("id") String id) throws IOException {
        con.sendGetRequest("https://dog.ceo/api/breeds/image/random");
        return Response.ok().build();
    }
}
