package ru.ras.economybot.telegram.commands;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import ru.ras.economybot.telegram.images.CallSchedulesImages;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class CallScheduleCommand implements ICommand {

    public static final String COMMAND_CALL_SCHEDULE = "Расписание звонков";

    private static final Set<String> callbacks = new HashSet<>();
    private static final Set<String> commands = new HashSet<>();

    public CallScheduleCommand() {
        commands.add(COMMAND_CALL_SCHEDULE);
    }

    @Override
    public Set<String> getCallbacks() {
        return callbacks;
    }

    @Override
    public Set<String> getCommands() {
        return commands;
    }

    @Override
    public BotApiMethod execute(final AbsSender absSender, final Update update, final CallbackQuery callbackQuery) {
        return null;
    }

    @Override
    public BotApiMethod execute(final AbsSender absSender, final Update update, final Message message) {
        if (message.getText().equals(COMMAND_CALL_SCHEDULE)) {
            return getSendMessageCallSchedule(absSender, message);
        }

        return null;
    }

    private SendMessage getSendMessageCallSchedule(final AbsSender absSender, final Message message) {
        try {
            absSender.execute(
                    new SendPhoto()
                            .setChatId(message.getChatId())
                            .setPhoto(new File(CallSchedulesImages.getImages()))
            );
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

        return new SendMessage()
                .setChatId(message.getChatId())
                .setText("Расписание звонков");
    }

}