package ru.ras.economybot.telegram.images;

import ru.ras.economybot.telegram.utils.ReadImagesUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ContactsImages {

    private static final String PATH_IMAGES = System.getProperty("user.dir") + "/images/contacts";

    private static final Map<String, String> images = new HashMap<>();

    static {
        init();
    }

    public static Map<String, String> getImages() {
        return images;
    }

    private static void init() {
        final Set<String> pathsImages = ReadImagesUtils.getPathsImages(PATH_IMAGES);

        for (String path : pathsImages) {
            images.put(getImageName(path), path);
        }
    }

    private static String getImageName(final String path) {
        final int index = path.lastIndexOf(File.separator) + 1;

        return path.substring(index, path.length() - 4);
    }

}