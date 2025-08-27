package com.example.urlchecker.controllers;

import com.example.urlchecker.models.NewLinesURLFile;
import com.example.urlchecker.models.URLFile;
import com.example.urlchecker.utils.Configuration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class URLFileProcessorTest {
    @Test
    public void parseFileTest() throws FileNotFoundException, URISyntaxException, MalformedURLException {
        URLFile file = new NewLinesURLFile(Configuration.TEST_FILEPATH);
        URL[] expectations = new URL[]{ new URI("https://google.com").toURL(), new URI("https://yandex.ru").toURL() };

        Assertions.assertArrayEquals(expectations, URLFileProcessor.parseFile(file));
    }
}
