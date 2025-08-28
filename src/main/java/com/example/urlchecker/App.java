package com.example.urlchecker;

import com.example.urlchecker.controllers.URLFileProcessor;
import com.example.urlchecker.controllers.URLFileProcessor.URLCheckerResult;
import com.example.urlchecker.libs.CLI;
import com.example.urlchecker.models.NewLinesURLFile;
import com.example.urlchecker.models.URLFile;
import com.example.urlchecker.utils.Configuration;
import com.example.urlchecker.view.Console;
import picocli.CommandLine;

import java.util.List;

public class App {
    public static void main(String[] args) {

        CLI cli = new CLI();

        try {
            new CommandLine(cli).parseArgs(args);
        } catch (Exception ex) {
            CommandLine.usage(cli, System.out);
            return;
        }

        if (cli.help) {
            CommandLine.usage(cli, System.out);
            return;
        }

        try {
            URLFile file = new NewLinesURLFile(cli.pathToURLs);
            List<URLCheckerResult> result = URLFileProcessor.check(file);
            Console view = new Console();
            view.display(result);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }

    }
}
