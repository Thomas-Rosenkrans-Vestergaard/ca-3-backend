package com.group3.ca3.data.repositories;

import com.group3.ca3.data.entities.File;
import com.group3.ca3.data.entities.User;

import java.util.List;

public interface FileRepository
{
    File create(String title, int size, String mime, String extension, String googleDriveId, User user);

    List<File> get();

    File get(long id);

    List<File> getByUser(User user);
}
