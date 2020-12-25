package com.security.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class PasswordWriter {
    private static final String FILE_PATH = "./src/main/resources/output/";

    public void writePasswordsToFile(List<String> passwords, String filename) {
        try {
            System.out.println("Writing to file : " + filename);
            Files.write(Path.of(FILE_PATH + filename), passwords, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error while writing to file: " + filename);
        }
    }
}
