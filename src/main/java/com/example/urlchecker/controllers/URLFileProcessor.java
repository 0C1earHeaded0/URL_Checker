package com.example.urlchecker.controllers;

import com.example.urlchecker.models.URLFile;
import com.example.urlchecker.utils.Configuration;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.*;
import java.util.stream.Stream;

public class URLFileProcessor {
    private static List<URLCheckerResult> checkerResults = new ArrayList<>();

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
                this.responseMsg = conn.getResponseCode() + " " + conn.getResponseMessage();
            }
        }
    }

    private static class CheckThread implements Runnable {
        private final Object url;

        public CheckThread(Object url) {
            this.url = url;
        }

        public void run() {
            setCheckerResults(url);
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

    private static void setCheckerResults(Object url) {
        try {
            if (url instanceof URL) {
                HttpURLConnection conn = getConn((URL) url);
                checkerResults.add(new URLCheckerResult(url.toString(), conn, null));
            } else {
                checkerResults.add(new URLCheckerResult((String)url, null, null));
            }
        } catch (IOException e) {
            try {
                assert url instanceof URL;
                checkerResults.add(new URLCheckerResult(url.toString(), null, e.getClass().getSimpleName()));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    private static HttpURLConnection getConn(URL url) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(5000);
        conn.setReadTimeout(5000);
        conn.setRequestProperty("Host", url.getHost());
        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (X11; Linux x86_64; rv:109.0) Gecko/20100101 Firefox/115.0");
        conn.setRequestProperty("Accept", "*/*");
        conn.setRequestProperty("Accept-Encoding", "gzip, deflate, br");
        conn.setRequestProperty("Accept-Language", "ru-RU,ru;q=0.8,en-US;q=0.5,en;q=0.3");
        conn.setRequestProperty("Connection", "close");
        return conn;
    }

    public static List<URLCheckerResult> check(URLFile file) {
        if (checkerResults.isEmpty()) {
            ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

            Object[] urls = parseFile(file);

            List<Runnable> tasks = Arrays.stream(urls).map(url -> (Runnable)new CheckThread(url)).toList();

            for (Runnable task: tasks) {
                executor.submit(task);
            }

            executor.shutdown();

            try {
                if (!executor.awaitTermination(3, TimeUnit.MINUTES)) {
                    System.err.println("Some tasks are still running. Forcing shutdown...");
                    executor.shutdownNow();
                }
            } catch (InterruptedException ignore) {
                executor.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }

        return checkerResults;
    }
}
