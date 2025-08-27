package com.example.urlchecker.controllers;

import com.example.urlchecker.models.URLFile;

import java.io.IOException;
import java.net.*;
import java.util.Arrays;
import java.util.Objects;

public class URLFileProcessor {
    private static URLCheckerResult[] checkerResults;

    public static class URLCheckerResult {
        public String url;
        public HttpURLConnection connection;
        public String responseMsg;

        public URLCheckerResult(String url, HttpURLConnection conn, String responseMsg) throws IOException {
            this.url = url;
            this.connection = conn;

            if (conn == null) {
                this.responseMsg = Objects.requireNonNullElse(responseMsg, "Wrong URL");
            } else {
                this.responseMsg = conn.getResponseMessage();
            }
        }
    }

    private static Object[] parseFile(URLFile file) {
        String[] urlStrings = file.getValues();

        try {
            return Arrays.stream(urlStrings)
                    .map(item -> {
                        try {
                            return new URI(item).toURL();
                        } catch (MalformedURLException | URISyntaxException e) {
                            return item;
                        }
                    })
                    .toArray(Object[]::new);
        } catch (RuntimeException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static URLCheckerResult[] check(URLFile file) {
        if (checkerResults == null) {
            synchronized (URLFileProcessor.class) {
                Object[] urls = parseFile(file);

                checkerResults = Arrays.stream(urls).map(item -> {
                    try {
                        if (item instanceof URL) {
                            return new URLCheckerResult(item.toString(), (HttpURLConnection) ((URL) item).openConnection(), null);
                        } else {
                            return new URLCheckerResult((String)item, null, null);
                        }
                    } catch (IOException e) {
                        try {
                            assert item instanceof URL;
                            return new URLCheckerResult(item.toString(), null, e.getClass().getSimpleName());
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
