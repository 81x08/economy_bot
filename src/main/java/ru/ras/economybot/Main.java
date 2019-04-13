package ru.ras.economybot;

import ru.ras.economybot.telegram.Telegram;
import ru.ras.economybot.telegram.directions.FactoryDirections;

public class Main {

    public static void main(String[] args) {
        init();

        new Telegram().start();
    }

    private static void init() {
        FactoryDirections.getInstance().init();
    }

}