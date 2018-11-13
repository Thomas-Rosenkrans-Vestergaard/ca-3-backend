package com.group3.ca3.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.group3.ca3.rest.http.*;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("files")
public class FileResource
{

    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @POST
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public Response upload(String json) throws HttpException
    {
        Received received = gson.fromJson(json, Received.class);

        HttpClient httpClient = new HttpClient();
        HttpRequest httpRequest = HttpRequestBuilder.getInstance()
                                                    .post("https://file.io")
                                                    .urlEncodedBody("text", received.data)
                                                    .acceptJSON()
                                                    .build();

        HttpResponse httpResponse = httpClient.perform(httpRequest);
        return Response.status(Response.Status.CREATED).build();
    }

    public static void main(String[] args) throws HttpException
    {

        HttpClient httpClient = new HttpClient();
        HttpRequest httpRequest = HttpRequestBuilder.getInstance()
                                                    .post("https://file.io")
                                                    .urlEncodedBody("text", "aids")
                                                    .acceptJSON()
                                                    .build();

        System.out.println(gson.toJson(httpRequest));
        HttpResponse httpResponse = httpClient.perform(httpRequest);
        System.out.println(gson.toJson(httpResponse));
    }

    private static class Received
    {
        public String fileName;
        public int    expire;
        public String data;
    }
}
