package com.group3.ca3.logic;

import com.group3.ca3.data.entities.File;
import com.group3.ca3.data.repositories.FileRepository;

import java.io.IOException;
import java.util.List;

public class FileFacade
{

    private final FileRepository    fileRepository;
    private final GoogleDriveFacade driveFacade;

    public FileFacade(FileRepository fileRepository, GoogleDriveFacade driveFacade)
    {
        this.fileRepository = fileRepository;
        this.driveFacade = driveFacade;
    }

    public File create(
            String title,
            String mime,
            String extension,
            byte[] data) throws IOException
    {
        com.google.api.services.drive.model.File uploadedFile = driveFacade.upload(title, mime, data);
        return fileRepository.create(title, data.length, mime, extension, uploadedFile.getId());
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
}
