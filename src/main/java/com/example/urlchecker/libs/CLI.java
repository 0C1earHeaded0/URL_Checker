package com.example.urlchecker.libs;

import picocli.CommandLine;

public class CLI {
    @CommandLine.Option(names = {"-f", "--file"}, description = "path to file contains url", required = true)
    public String pathToURLs;

    @CommandLine.Option(names = { "-o", "--output" }, description = "path to output file")
    public String pathToOut;

    @CommandLine.Option(names = { "-h", "--help" }, description = "show help")
    public boolean help;
}
