import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Stream;

public class ReadSchedulesImages {

    private static final String PATH_SCHEDULES_IMAGES = System.getProperty("user.dir") + "/tg_economy/schedulers";

    private static final Map<String, Set<String>> groups = new HashMap<>();

    @Test
    public void begin() {
        try (Stream<Path> stream = Files.walk(Paths.get(PATH_SCHEDULES_IMAGES))) {
            stream.forEach(pathStream -> {
                final String path = pathStream.toString();

                if (path.endsWith(".png")) {
                    final int symbolSlashId = path.lastIndexOf("\\") + 1;

                    final String fileName = path.substring(symbolSlashId, path.length() - (path.contains("^") ? 6 : 4));

                    for (String groupId : fileName.split("_")) {
                        Set<String> images = new TreeSet<>();

                        if (groups.containsKey(groupId)) {
                            images = groups.get(groupId);

                            images.add(path);
                        } else {
                            images.add(path);
                        }

                        groups.put(groupId, images);
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        groups.forEach((k, v) -> System.out.println(k + " : " +  v));
    }

}