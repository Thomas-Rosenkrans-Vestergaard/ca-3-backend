package com.group3.ca3.rest.dto;

import com.group3.ca3.data.entities.File;

import java.time.LocalDateTime;

public class FileDTO
{

    public final Long          id;
    public final String        title;
    public final int           size;
    public final String        mime;
    public final String        extension;
    public final String        fileIOKey;
    public final LocalDateTime expiry;

    public FileDTO(File file)
    {
        this.id = file.getId();
        this.title = file.getTitle();
        this.size = file.getSize();
        this.mime = file.getMime();
        this.extension = file.getExtension();
        this.fileIOKey = file.getFileIOKey();
        this.expiry = file.getExpiry();
    }
}
