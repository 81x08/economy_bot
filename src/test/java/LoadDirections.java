import javafx.util.Pair;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class LoadDirections {

    private static final Logger logger = LoggerFactory.getLogger(LoadDirections.class);

    private final Set<Pair<String, String>> directions = new LinkedHashSet<>();
    private final Map<Pair<String, String>, Set<String>> groupsDirections = new LinkedHashMap<>();

    @Test
    public void begin() {
        final JSONParser jsonParser = new JSONParser();

        try {
            final Object object = jsonParser.parse(
                    new InputStreamReader(
                            getClass().getClassLoader().getResourceAsStream("economy.json"),
                            StandardCharsets.UTF_8
                    )
            );

            final JSONObject jsonObject = (JSONObject) object;

            final JSONArray jsonArrayDirections = (JSONArray) jsonObject.get("directions");

            for (Object direction : jsonArrayDirections) {
                final JSONObject jsonObjectDirection = (JSONObject) direction;

                final String name = (String) jsonObjectDirection.get("name");
                final String callback = (String) jsonObjectDirection.get("callback");

                final Pair<String, String> pairDirection = new Pair<>(name, callback);

                directions.add(pairDirection);

                final JSONArray jsonArrayGroups = (JSONArray) jsonObjectDirection.get("groups");

                final Set<String> groups = new LinkedHashSet<>();

                for (Object group : jsonArrayGroups) {
                    final String groupName = (String) group;

                    groups.add(groupName);
                }

                groupsDirections.put(pairDirection, groups);
            }
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }

        System.out.println(groupsDirections);
    }

    private void getGroups() {
        for (Pair<String, String> pair : directions) {
            logger.error(pair.toString());
        }
    }

}