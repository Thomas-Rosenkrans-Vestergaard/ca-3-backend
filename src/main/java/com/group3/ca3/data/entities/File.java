package com.group3.ca3.data.entities;

import javax.persistence.*;

@Entity
@Table(name = "files_")
public class File
{

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private int size;

    @Column(nullable = false)
    private String mime;

    @Column(nullable = false)
    private String extension;

    @Column(nullable = false)
    private String googleDriveId;

    public File()
    {

    }

    public File(String title, int size, String mime, String extension, String googleDriveId)
    {
        this.title = title;
        this.size = size;
        this.mime = mime;
        this.extension = extension;
        this.googleDriveId = googleDriveId;
    }

    public Long getId()
    {
        return this.id;
    }

    public File setId(Long id)
    {
        this.id = id;
        return this;
    }

    public String getTitle()
    {
        return this.title;
    }

    public File setTitle(String title)
    {
        this.title = title;
        return this;
    }

    public int getSize()
    {
        return this.size;
    }

    public File setSize(int size)
    {
        this.size = size;
        return this;
    }

    public String getMime()
    {
        return this.mime;
    }

    public File setMime(String mime)
    {
        this.mime = mime;
        return this;
    }

    public String getExtension()
    {
        return this.extension;
    }

    public File setExtension(String extension)
    {
        this.extension = extension;
        return this;
    }

    public String getGoogleDriveId()
    {
        return this.googleDriveId;
    }

    public File setGoogleDriveId(String googleDriveId)
    {
        this.googleDriveId = googleDriveId;
        return this;
    }
}
