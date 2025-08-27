package com.example.urlchecker;

import com.example.urlchecker.controllers.URLFileProcessor;
import com.example.urlchecker.controllers.URLFileProcessor.URLCheckerResult;
import com.example.urlchecker.models.NewLinesURLFile;
import com.example.urlchecker.models.URLFile;
import com.example.urlchecker.utils.Configuration;
import com.example.urlchecker.view.Console;

public class App {
    public static void main(String[] args) {
        try {
            URLFile file = new NewLinesURLFile(Configuration.TEST_FILEPATH);
            URLCheckerResult[] result = URLFileProcessor.check(file);
            Console view = new Console();
            view.display(result);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }
}
