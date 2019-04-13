package ru.ras.economybot.telegram.commands;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;

import ru.ras.economybot.telegram.utils.KeyboardUtils;

import java.util.HashSet;
import java.util.Set;

public class StartCommand implements ICommand {

    private static final String COMMAND_START = "/start";

    private static final Set<String> callbacks = new HashSet<>();
    private static final Set<String> commands = new HashSet<>();

    public StartCommand() {
        commands.add(COMMAND_START);
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
        if (message.getText().equals(COMMAND_START)) {
            return getSendMessageStart(message);
        }

        return null;
    }

    private SendMessage getSendMessageStart(final Message message) {
        final String stringBuilder = "Привет! На связи бот института экономики и управления!\n" +
                "Если тебе нужна будет помощь, то загляни в меню, там ты сможешь найти всё, что тебе нужно!";

        return new SendMessage()
                .setChatId(message.getChatId())
                .setText(stringBuilder)
                .setReplyMarkup(KeyboardUtils.getReplyKeyboardMarkupMain());
    }

}