package com.group3.ca3.rest.dto;

import com.group3.ca3.data.entities.File;

public class FileDTO
{

    public final Long   id;
    public final String title;
    public final int    size;
    public final String mime;
    public final String extension;

    public FileDTO(File file)
    {
        this.id = file.getId();
        this.title = file.getTitle();
        this.size = file.getSize();
        this.mime = file.getMime();
        this.extension = file.getExtension();
    }
}
