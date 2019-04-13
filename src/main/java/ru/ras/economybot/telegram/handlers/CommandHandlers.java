package ru.ras.economybot.telegram.handlers;

import ru.ras.economybot.telegram.commands.ICommand;

import java.util.HashMap;
import java.util.Map;

public class CommandHandlers {

    private static final Map<Object, Object> commands = new HashMap<>();

    public void registerCommand(final ICommand handler) {
        processing(handler);
    }

    public Map<Object, Object> getEvents() {
        return commands;
    }

    private void processing(final ICommand handler) {
        handler.getCallbacks().forEach(callback -> addEvents(callback, handler));
        handler.getCommands().forEach(command -> addEvents(command, handler));
    }

    private void addEvents(final Object command, final ICommand handler) {
        commands.put(command, handler);
    }

}