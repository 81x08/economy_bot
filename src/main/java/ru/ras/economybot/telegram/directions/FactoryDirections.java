package ru.ras.economybot.telegram.directions;

import javafx.util.Pair;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class FactoryDirections {

    private static volatile FactoryDirections instance;

    private final Set<Pair<String, String>> directions = new LinkedHashSet<>();
    private final Map<Pair<String, String>, Set<String>> groupsDirections = new LinkedHashMap<>();

    private FactoryDirections() {}

    public static FactoryDirections getInstance() {
        if (instance == null) {
            synchronized (FactoryDirections.class) {
                if (instance == null) {
                    instance = new FactoryDirections();
                }
            }
        }

        return instance;
    }

    public FactoryDirections init() {
        loadDirections();

        return instance;
    }

    public Set<Pair<String, String>> getDirections() {
        return directions;
    }

    public Pair<String, String> getPairDirection(final String second) {
        for (Pair<String, String> pair : directions) {
            if (pair.getKey().equals(second) || pair.getValue().equals(second)) {
                return pair;
            }
        }

        return null;
    }

    public Set<String> getGroups(final Pair<String, String> direction) {
        return groupsDirections.get(direction);
    }

    private void loadDirections() {
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
    }

}