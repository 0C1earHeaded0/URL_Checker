package com.example.urlchecker.view;

import com.example.urlchecker.controllers.URLFileProcessor.URLCheckerResult;

import java.util.List;

public interface URLResultDisplay {
    void display(List<URLCheckerResult> result);
}
