package com.group3.ca3.logic;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.InputStreamContent;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.group3.ca3.data.entities.File;
import com.group3.ca3.data.repositories.FileRepository;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

public class FileFacade
{

    private final        FileRepository fileRepository;
    private static final String         APPLICATION_NAME      = "Ca3 Drive Backend";
    private static final JsonFactory    JSON_FACTORY          = JacksonFactory.getDefaultInstance();
    private static final String         TOKENS_DIRECTORY_PATH = "tokens";
    private static final List<String>   SCOPES                = Collections.singletonList(DriveScopes.DRIVE_METADATA_READONLY);
    private static final String         CREDENTIALS_FILE_PATH = "/credentials.json";
    private final        Drive          drive;

    public FileFacade(FileRepository fileRepository)
    {
        try {
            this.fileRepository = fileRepository;
            this.drive = getDriveInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public File create(
            String title,
            int size,
            String mime,
            String extension,
            byte[] data) throws IOException
    {
        File                                     file     = fileRepository.create(title, size, mime, extension);
        com.google.api.services.drive.model.File toUpload = new com.google.api.services.drive.model.File();
        toUpload.setId(file.getId().toString());

        Drive.Files.Create createRequest = drive
                .files()
                .create(toUpload, new InputStreamContent(mime, new ByteArrayInputStream(data)));

        com.google.api.services.drive.model.File uploadedFile = createRequest.execute();
        return file;
    }

    public List<File> get()
    {
        return fileRepository.get();
    }

    public File get(long id)
    {
        return fileRepository.get(id);
    }

    public List<File> search(String title)
    {
        return fileRepository.search(title);
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
        // Load client secrets.
        InputStream         in            = FileFacade.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
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
