package ru.ras.economybot.telegram.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

public class ReadImagesUtils {

    public static Set<String> getPathsImages(final String folderPath) {
        final Set<String> pathsImages = new HashSet<>();

        try (Stream<Path> stream = Files.walk(Paths.get(folderPath))) {
            stream.forEach(pathStream -> {
                final String path = pathStream.toString();

                if (path.endsWith(".png")) {
                    pathsImages.add(path);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        return pathsImages;
    }

}