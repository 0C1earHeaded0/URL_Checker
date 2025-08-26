package com.example.urlchecker.models;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class NewLinesURLFile extends URLFile {
    public NewLinesURLFile(String path) throws FileNotFoundException {
        this.description = "Each url address starts on a new line.";
        this.file = new File(path.trim());
        this.checkFileExistence();
    }

    @Override
    public String[] getValues() {
        try (FileInputStream in = new FileInputStream(file)) {
            return new String(in.readAllBytes(), StandardCharsets.UTF_8).split("\n");
        } catch (IOException ignored) {}

        return new String[0];
    }
}
