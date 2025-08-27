package com.example.urlchecker.controllers;

import com.example.urlchecker.models.NewLinesURLFile;
import com.example.urlchecker.models.URLFile;
import com.example.urlchecker.utils.Configuration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import com.example.urlchecker.controllers.URLFileProcessor.URLCheckerResult;

import java.io.IOException;
import java.net.*;

public class URLFileProcessorTest {
    @Test
    public void checkTest() throws URISyntaxException, IOException {
        URLFile file = new NewLinesURLFile(Configuration.TEST_FILEPATH);
        URL[] expectURLs = new URL[]{ new URI("https://google.com").toURL(), new URI("https://yandex.ru").toURL() };

        URLCheckerResult[] expectResults = new URLCheckerResult[]{
                new URLCheckerResult(expectURLs[0], (HttpURLConnection) expectURLs[0].openConnection()),
                new URLCheckerResult(expectURLs[1], (HttpURLConnection) expectURLs[1].openConnection())
        };

        String expectedResult = expectResults[0].url.getPath();
        String realResult = URLFileProcessor.check(file)[0].url.getPath();

        Assertions.assertEquals(expectedResult, realResult);
    }
}
