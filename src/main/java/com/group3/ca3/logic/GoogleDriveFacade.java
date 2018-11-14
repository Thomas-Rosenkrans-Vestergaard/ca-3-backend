package com.group3.ca3.logic;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.InputStreamContent;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

public class GoogleDriveFacade
{

    private static final String      APPLICATION_NAME      = "Ca3 Drive Backend";
    private static final JsonFactory JSON_FACTORY          = JacksonFactory.getDefaultInstance();
    private static final String      CREDENTIALS_FILE_PATH = "/ca-3-222510-a9b074a97d9e.json";
    private final        Drive       drive;

    public GoogleDriveFacade()
    {
        try {
            drive = getDriveInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public File upload(String title, String mime, byte[] data) throws IOException
    {
        com.google.api.services.drive.model.File toUpload = new com.google.api.services.drive.model.File();
        toUpload.setName(title);

        Drive.Files.Create createRequest = drive
                .files()
                .create(toUpload, new InputStreamContent(mime, new ByteArrayInputStream(data)));

        return createRequest.execute();
    }

    public byte[] download(String id) throws IOException
    {
        ByteArrayOutputStream out     = new ByteArrayOutputStream();
        Drive.Files.Get       request = drive.files().get(id);
        request.executeMediaAndDownloadTo(out);
        return out.toByteArray();
    }

    /**
     * Creates an authorized Credential object.
     *
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    private Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException
    {
        return GoogleCredential.fromStream(FileFacade.class.getResourceAsStream(CREDENTIALS_FILE_PATH))
                               .createScoped(Collections.singletonList(DriveScopes.DRIVE));
    }

    private Drive getDriveInstance() throws IOException, GeneralSecurityException
    {
        // Build a new authorized API client service.
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        return new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
    }
}
