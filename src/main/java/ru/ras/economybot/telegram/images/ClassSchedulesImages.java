package ru.ras.economybot.telegram.images;

import ru.ras.economybot.telegram.utils.ReadImagesUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class ClassSchedulesImages {

    private static final String PATH_IMAGES = System.getProperty("user.dir") + "/images/schedules";

    private static final Map<String, Set<String>> images = new HashMap<>();

    static {
        init();
    }

    public static Map<String, Set<String>> getImages() {
        return images;
    }

    private static void init() {
        final Set<String> pathsImages = ReadImagesUtils.getPathsImages(PATH_IMAGES);

        for (String path : pathsImages) {
            final String name = getImageName(path);

            for (String groupId : name.split("_")) {
                final Set<String> folderImages;

                if (images.containsKey(groupId)) {
                    folderImages = images.get(groupId);

                    folderImages.add(path);
                } else {
                    folderImages = new TreeSet<>();

                    folderImages.add(path);
                }

                images.put(groupId, folderImages);
            }
        }

        System.out.println(images);
    }

    private static String getImageName(final String path) {
        final int index = path.lastIndexOf(File.separator) + 1;

        return path.substring(index, path.length() - (path.contains("^") ? 6 : 4));
    }

}