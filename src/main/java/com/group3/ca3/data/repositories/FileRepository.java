package com.group3.ca3.data.repositories;

import com.group3.ca3.data.entities.File;

import java.util.List;

public interface FileRepository
{

    File create(String title, int size, String mime, String extension);

    List<File> get();

    List<File> getByUser(long user);
}
