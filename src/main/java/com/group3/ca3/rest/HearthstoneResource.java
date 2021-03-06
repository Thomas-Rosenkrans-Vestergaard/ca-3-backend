package com.group3.ca3.rest;

import com.google.gson.*;
import com.group3.ca3.rest.http.HttpResponse;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Path("hearthstone")
public class HearthstoneResource
{


    private static CreateConnection     con  = new CreateConnection();
    private static HearthstoneCardCache hearthstoneCardCache;
    private static Gson                 gson = new GsonBuilder().setPrettyPrinting().create();

    static {
        try {
            HttpResponse          response = con.sendGetRequest("https://omgvamp-hearthstone-v1.p.mashape.com/cards", true);
            List<HearthstoneCard> list     = deserialize(response.contents);
            hearthstoneCardCache = new HearthstoneCardCache(list);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static List<HearthstoneCard> deserialize(String json)
    {
        JsonParser parser = new JsonParser();
        JsonObject root   = parser.parse(json).getAsJsonObject();

        List<HearthstoneCard> cards = new ArrayList<>();
        for (Map.Entry<String, JsonElement> entry : root.entrySet()) {
            JsonArray elements = entry.getValue().getAsJsonArray();
            for (JsonElement element : elements) {
                JsonObject object = element.getAsJsonObject();
                cards.add(new HearthstoneCard(
                        getPrimitiveString(object, "flavor"),
                        getPrimitiveInt(object, "attack"),
                        getPrimitiveInt(object, "health"),
                        getPrimitiveString(object, "img"),
                        getPrimitiveInt(object, "cost"),
                        getPrimitiveString(object, "name"),
                        getPrimitiveString(object, "text"),
                        getPrimitiveString(object, "cardSet")
                ));
            }
        }

        return cards;
    }

    private static String getPrimitiveString(JsonObject object, String key)
    {
        JsonPrimitive primitive = object.getAsJsonPrimitive(key);
        return primitive == null ? null : primitive.getAsString();
    }

    private static Integer getPrimitiveInt(JsonObject object, String key)
    {
        JsonPrimitive primitive = object.getAsJsonPrimitive(key);
        return primitive == null ? null : primitive.getAsInt();
    }

    @GET
    @Path("all")
    public Response getAllCards() throws IOException
    {
        HttpResponse response = con.sendGetRequest("https://omgvamp-hearthstone-v1.p.mashape.com/cards", true);
        return Response.status(response.responseCode).entity(response.contents).build();
    }

    @GET
    @Path("paginated/{pageSize}/{pageNumber}")
    public Response getPaginated(@PathParam("pageSize") int pageSize, @PathParam("pageNumber") int pageNumber) throws IOException
    {
        List<HearthstoneCard> results = hearthstoneCardCache.getPaginated(pageSize, pageNumber);
        return Response.ok(gson.toJson(new PaginatedResponse(hearthstoneCardCache.size(), pageNumber, results))).build();
    }

    @GET
    @Path("search/{name}")
    public Response getCardByName(@PathParam("name") String name) throws IOException
    {
        HttpResponse response = con.sendGetRequest("https://omgvamp-hearthstone-v1.p.mashape.com/cards/search/" + name + "", true);
        return Response.status(response.responseCode).entity(response.contents).build();
    }

    private class PaginatedResponse
    {
        public int                   total;
        public int                   pageNumber;
        public List<HearthstoneCard> results;

        public PaginatedResponse(int total, int pageNumber, List<HearthstoneCard> results)
        {
            this.total = total;
            this.pageNumber = pageNumber;
            this.results = results;
        }
    }
}
