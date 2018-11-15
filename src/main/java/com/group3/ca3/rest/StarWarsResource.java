package com.group3.ca3.rest;

import com.group3.ca3.rest.http.HttpClient;
import com.group3.ca3.rest.http.HttpRequest;
import com.group3.ca3.rest.http.HttpResponse;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("star-wars")
public class StarWarsResource
{

    @GET
    @Produces(APPLICATION_JSON)
    public Response getInformation() throws Exception
    {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        List<StarWarsRequest> requests = Arrays.asList(
                new StarWarsRequest("films", HttpRequest.builder().header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36").acceptJSON().get("https://swapi.co/api/films/").build()),
                new StarWarsRequest("people", HttpRequest.builder().header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36").acceptJSON().get("https://swapi.co/api/people/").build()),
                new StarWarsRequest("planets", HttpRequest.builder().header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36").acceptJSON().get("https://swapi.co/api/planets/").build()),
                new StarWarsRequest("species", HttpRequest.builder().header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36").acceptJSON().get("https://swapi.co/api/species/").build()),
                new StarWarsRequest("starships", HttpRequest.builder().header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36").acceptJSON().get("https://swapi.co/api/starships/").build()),
                new StarWarsRequest("vehicles", HttpRequest.builder().header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36").acceptJSON().get("https://swapi.co/api/vehicles/").build())
        );

        HttpClient                     httpClient = new HttpClient();
        List<Future<StarWarsResponse>> futures    = new ArrayList<>();
        for (StarWarsRequest request : requests) {
            Future<StarWarsResponse> future = executorService.submit(
                    () -> new StarWarsResponse(
                            request.key,
                            request.httpRequest,
                            httpClient.perform(request.httpRequest)));

            futures.add(future);
        }

        StringBuilder builder = new StringBuilder();
        builder.append("{");

        int size = futures.size();
        for (int x = 0; x < size; x++) {
            StarWarsResponse response = futures.get(x).get();
            builder.append('"');
            builder.append(response.key);
            builder.append('"');
            builder.append(':');
            builder.append(response.httpResponse.contents);
            if (x < size - 1)
                builder.append(',');
        }

        builder.append('}');

        return Response.ok(builder.toString()).build();
    }

    private class StarWarsResponse extends StarWarsRequest
    {
        public final HttpResponse httpResponse;

        public StarWarsResponse(String key, HttpRequest httpRequest, HttpResponse httpResponse)
        {
            super(key, httpRequest);
            this.httpResponse = httpResponse;
        }
    }

    private class StarWarsRequest
    {
        public final String      key;
        public final HttpRequest httpRequest;

        public StarWarsRequest(String key, HttpRequest httpRequest)
        {
            this.key = key;
            this.httpRequest = httpRequest;
        }
    }
}
