package com.example.urlchecker.view;

import com.example.urlchecker.controllers.URLFileProcessor.URLCheckerResult;

import java.util.List;

public class Console implements URLResultDisplay{
    public void display(List<URLCheckerResult> result) {
        for (URLCheckerResult item: result) {
            if (item.url != null) {
                System.out.printf("URL: %s Status: %s\n", item.url, item.responseMsg);
            }
        }
    }
}
