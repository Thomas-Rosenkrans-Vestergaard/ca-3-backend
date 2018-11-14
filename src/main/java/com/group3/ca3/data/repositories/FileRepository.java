package com.group3.ca3.data.repositories;

import com.group3.ca3.data.entities.File;

import javax.persistence.Column;
import java.time.LocalDateTime;
import java.util.List;

public interface FileRepository
{
    File create(String title, int size, String mime, String extension, String googleDriveId);

    List<File> get();

    File get(long id);

    List<File> search(String title);

    List<File> getByUser(long user);

    List<File> searchByUser(String title, long user);
}
