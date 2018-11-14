package com.group3.ca3.rest;

import com.google.gson.Gson;
import com.group3.ca3.data.entities.File;
import com.group3.ca3.data.repositories.JpaFileRepository;
import com.group3.ca3.logic.FileFacade;
import com.group3.ca3.logic.GoogleDriveFacade;
import com.group3.ca3.rest.dto.FileDTO;
import com.group3.ca3.rest.http.HttpException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.Base64;
import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("files")
public class FileResource
{

    private static       Gson              gson        = SpecializedGson.create();
    private static final GoogleDriveFacade driveFacade = new GoogleDriveFacade();
    private static final FileFacade        fileFacade  = new FileFacade(new JpaFileRepository(JpaConnection.create()),
                                                                        driveFacade);

    @GET
    @Produces(APPLICATION_JSON)
    public Response get() throws HttpException
    {
        List<File> files = fileFacade.get();

        return Response.ok(gson.toJson(files)).build();
    }

    @POST
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public Response upload(String json) throws HttpException, IOException
    {
        Received received = gson.fromJson(json, Received.class);

        byte[] bytes = Base64.getDecoder().decode(received.data);
        String mime  = getMimeType(bytes);
        if (mime == null)
            throw new IllegalArgumentException("Bad mime type");

        File    file    = fileFacade.create(received.title, mime, received.extension, bytes);
        FileDTO fileDTO = new FileDTO(file);
        return Response.status(Response.Status.CREATED).entity(gson.toJson(fileDTO)).build();
    }

    @GET
    @Path("download/{id: [0-9]+}")
    public Response download(@PathParam("id") long id) throws Exception
    {
        File file = fileFacade.get(id);
        if (file == null)
            throw new IllegalStateException("404 file");

        return Response
                .ok(driveFacade.download(file.getGoogleDriveId()), MediaType.APPLICATION_OCTET_STREAM)
                .header("content-disposition", "attachment; filename = " + file.getId() + "." + file.getExtension())
                .build();
    }

    private static class ResponseEntity
    {
        public boolean success;
        public String  key;
    }

    private static class Received
    {
        public String title;
        public String extension;
        public String data;
    }

    /**
     * Finds the mime type of the image in the provided byte data.
     *
     * @param data The image byte data to return the mime type of.
     * @return The mime type of the image in the provided byte data. {@code null} when the mime type could not be
     * read from the image byte data.
     */
    private String getMimeType(byte[] data)
    {
        try {
            InputStream is = new ByteArrayInputStream(data);
            return URLConnection.guessContentTypeFromStream(is);
        } catch (IOException e) {
            return null;
        }
    }
}
