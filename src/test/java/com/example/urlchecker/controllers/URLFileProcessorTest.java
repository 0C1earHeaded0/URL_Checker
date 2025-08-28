package com.example.urlchecker.controllers;

import com.example.urlchecker.models.NewLinesURLFile;
import com.example.urlchecker.models.URLFile;
import com.example.urlchecker.utils.Configuration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import com.example.urlchecker.controllers.URLFileProcessor.URLCheckerResult;

import java.io.IOException;
import java.net.*;
import java.util.Objects;

public class URLFileProcessorTest {
    @Test
    public void checkTest() throws URISyntaxException, IOException {
        URLFile file = new NewLinesURLFile(Configuration.TEST_FILEPATH);
        URL[] expectURLs = new URL[]{ new URI("https://google.com").toURL(), new URI("https://yandex.ru").toURL(), new URI("http://asdfsdaf.net").toURL() };

        URLCheckerResult[] expectResults = new URLCheckerResult[]{
                new URLCheckerResult(expectURLs[0].toString(), (HttpURLConnection) expectURLs[0].openConnection(), null),
                new URLCheckerResult(expectURLs[1].toString(), (HttpURLConnection) expectURLs[1].openConnection(), null)
        };

        String expectedResult = expectResults[0].url;
        String realResult = null;

        for (URLCheckerResult result :URLFileProcessor.check(file)) {
            if (Objects.equals(result.url, expectedResult)) {
                realResult = result.url;
            }
        }

        Assertions.assertEquals(expectedResult, realResult);
    }
}
