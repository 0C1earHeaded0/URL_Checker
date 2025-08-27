package com.example.urlchecker.controllers;

import com.example.urlchecker.models.URLFile;

import java.io.IOException;
import java.net.*;
import java.util.Arrays;

public class URLFileProcessor {
    private static URLCheckerResult[] checkerResults;

    public static class URLCheckerResult {
        public URL url;
        public HttpURLConnection connection;
        public String responseMsg;

        public URLCheckerResult(URL url, HttpURLConnection conn) throws IOException {
            this.url = url;
            this.connection = conn;
            this.responseMsg = conn == null? "" : conn.getResponseMessage();
        }
    }

    private static URL[] parseFile(URLFile file) {
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

    public static URLCheckerResult[] check(URLFile file) {
        if (checkerResults == null) {
            synchronized (URLFileProcessor.class) {
                URL[] urls = parseFile(file);

                checkerResults = Arrays.stream(urls).map(item -> {
                    try {
                        return new URLCheckerResult(item, (HttpURLConnection) item.openConnection());
                    } catch (IOException e) {
                        try {
                            System.out.println(e.getMessage());
                            return new URLCheckerResult(null, null);
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }).toArray(URLCheckerResult[]::new);
            }
        }

        return checkerResults;
    }
}
