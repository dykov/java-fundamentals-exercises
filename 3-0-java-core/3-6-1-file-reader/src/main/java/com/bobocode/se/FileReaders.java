package com.bobocode.se;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * {@link FileReaders} provides an API that allow to read whole file into a {@link String} by file name.
 */
public class FileReaders {

    /**
     * Returns a {@link String} that contains whole text from the file specified by name.
     *
     * @param fileName a name of a text file
     * @return string that holds whole file content
     */
    public static String readWholeFile(String fileName) {
        final Path filePath = getFilePath(fileName);
        try (Stream<String> lines = Files.lines(filePath)) {
            return lines.collect(Collectors.joining("\n"));
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Path getFilePath(final String filename) {
        final URL url = FileReaders.class.getClassLoader().getResource(filename);
        if(url == null) {
            throw new RuntimeException("Illegal file URI: filename=" + filename);
        }
        try {
            return Paths.get(url.toURI());
        } catch(URISyntaxException e) {
            throw new RuntimeException("Illegal file URI", e);
        }
    }
}
