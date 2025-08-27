package com.example.urlchecker.view;

import com.example.urlchecker.controllers.URLFileProcessor.URLCheckerResult;

public interface URLResultDisplay {
    void display(URLCheckerResult[] result);
}
