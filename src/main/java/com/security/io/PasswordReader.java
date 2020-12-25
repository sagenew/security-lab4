package com.security.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class PasswordReader {
    protected static final String FILE_PATH = "./src/main/resources/input/";

    public static List<String> readPasswordsFromFile(String filename) {
        try {
            System.out.println("Reading file : " + filename );
            return Files.lines(Path.of(FILE_PATH + filename)).collect(Collectors.toList());
        } catch (IOException e) {
            System.err.println("Can not read file: " + filename);
            return Collections.emptyList();
        }
    }
}
