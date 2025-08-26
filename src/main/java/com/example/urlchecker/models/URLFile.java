package com.example.urlchecker.models;

import java.io.File;
import java.io.FileNotFoundException;

public abstract class URLFile {
    String description;
    File file;

    protected void checkFileExistence() throws FileNotFoundException {
        if (!file.exists()) {
            throw new FileNotFoundException(String.format("File with URL: %s not found.", file.getAbsolutePath()));
        }
    }

    abstract String[] getValues();
}
