package com.bobocode.se;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * {@link FileStats} provides an API that allow to get character statistic based on text file. All whitespace characters
 * are ignored.
 */
public class FileStats {

    private final Map<Character, Long> charCountMap;
    private final char mostPopularCharacter;


    private FileStats(final String fileName) {
        charCountMap = getCharCountMap(fileName);
        mostPopularCharacter = getMostPopularCharacter(charCountMap);
    }

    private char getMostPopularCharacter(final Map<Character, Long> countMap) {
        return countMap.entrySet().stream()
                .max(Comparator.comparingLong(Map.Entry::getValue))
                .orElseThrow(() -> new FileStatsException("Char map has not been filled"))
                .getKey();
    }

    private Map<Character, Long> getCharCountMap(final String fileName) {
        final Path filePath = getFilePath(fileName);
        try(final Stream<String> lines = Files.lines(filePath)) {
            return collectMap(lines);
        } catch(IOException e) {
            throw new FileStatsException("File" + fileName + " cannot be read", e);
        }
    }

    private Map<Character, Long> collectMap(final Stream<String> lines) {
        return lines.flatMapToInt(String::chars)
                .filter(ch -> ch != 32)
                .mapToObj(ch -> (char) ch)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }

    private static Path getFilePath(final String fileName) {
        final URL url = getFileUrl(fileName);
        try {
            return Paths.get(url.toURI());
        } catch(URISyntaxException e) {
            throw new FileStatsException("Incorrect file URI", e);
        }
    }

    private static URL getFileUrl(final String fileName) {
        final URL url = FileStats.class.getClassLoader().getResource(fileName);
        if(url == null) {
            throw new FileStatsException("Incorrect file URL: filename=" + fileName);
        }
        return url;
    }

    /**
     * Creates a new immutable {@link FileStats} objects using data from text file received as a parameter.
     *
     * @param fileName input text file name
     * @return new FileStats object created from text file
     */
    public static FileStats from(String fileName) {
        return new FileStats(fileName);
    }

    /**
     * Returns a number of occurrences of the particular character.
     *
     * @param character a specific character
     * @return a number that shows how many times this character appeared in a text file
     */
    public int getCharCount(char character) {
        return charCountMap.get(character).intValue();
    }

    /**
     * Returns a character that appeared most often in the text.
     *
     * @return the most frequently appeared character
     */
    public char getMostPopularCharacter() {
        return mostPopularCharacter;
    }

    /**
     * Returns {@code true} if this character has appeared in the text, and {@code false} otherwise
     *
     * @param character a specific character to check
     * @return {@code true} if this character has appeared in the text, and {@code false} otherwise
     */
    public boolean containsCharacter(char character) {
        return charCountMap.containsKey(character);
    }
}
