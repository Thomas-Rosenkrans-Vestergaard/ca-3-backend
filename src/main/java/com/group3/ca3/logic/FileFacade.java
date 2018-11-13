package com.group3.ca3.logic;

import com.group3.ca3.data.entities.File;
import com.group3.ca3.data.repositories.FileRepository;

import java.time.LocalDateTime;
import java.util.List;

public class FileFacade
{

    private final FileRepository fileRepository;

    public FileFacade(FileRepository fileRepository)
    {
        this.fileRepository = fileRepository;
    }

    public File create(String title, int size, String mime, String extension, String fileIOKey, LocalDateTime expiry)
    {
        return fileRepository.create(title, size, mime, extension, fileIOKey, expiry);
    }

    public List<File> get()
    {
        return fileRepository.get();
    }

    public File get(long id){
        return fileRepository.get(id);
    }

    public List<File> search(String title)
    {
        return fileRepository.search(title);
    }
}
