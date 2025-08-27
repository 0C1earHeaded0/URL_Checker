package com.example.urlchecker.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.io.FileNotFoundException;

import static com.example.urlchecker.utils.Configuration.TEST_FILEPATH;

public class NewLinesURLFileTest {
    @Test
    public void testGetValues() throws FileNotFoundException {
        NewLinesURLFile instance = new NewLinesURLFile(TEST_FILEPATH);
        String[] ReceivedValue = instance.getValues();
        Assertions.assertArrayEquals(new String[]{"https://google.com", "https://yandex.ru", "http://asdfsdaf.net"}, ReceivedValue);
    }
}
