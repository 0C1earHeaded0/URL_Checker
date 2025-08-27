package com.example.urlchecker.controllers;

import com.example.urlchecker.models.URLFile;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URI;
import java.util.Arrays;

public class URLFileProcessor {
    public static URL[] parseFile(URLFile file) {
        String[] urlStrings = file.getValues();

        try {
            return Arrays.stream(urlStrings)
                    .map(item -> {
                        try {
                            return new URI(item).toURL();
                        } catch (MalformedURLException | URISyntaxException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .toArray(URL[]::new);
        } catch (RuntimeException ex) {
            throw new RuntimeException(ex);
        }
    }
}
