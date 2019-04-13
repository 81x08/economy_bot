package ru.ras.economybot.telegram.commands;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.bots.AbsSender;

import ru.ras.economybot.telegram.keyboard.InlineKeyboardMarkupBuilder;

import java.util.HashSet;
import java.util.Set;

public class UsefulLinksCommand implements ICommand {

    public static final String COMMAND_USEFUL_LINKS = "Полезные ссылки";

    private static final String NAME_BUTTON_SITE = "Сайт";
    private static final String NAME_BUTTON_PROVISIONS = "Положения";
    private static final String NAME_BUTTON_ARTICLE_ASSOCIATION = "Устав";

    private static final String URL_SITE = "http://www.surgu.ru";
    private static final String URL_PROVISIONS = "http://www.surgu.ru/zhizn-surgu/studencheskie-meropriyatiya/polozheniya";
    private static final String URL_ARTICLE_ASSOCIATION = "http://web.surgu.ru/upload/35981-ustav.pdf";

    private static final Set<String> callbacks = new HashSet<>();
    private static final Set<String> commands = new HashSet<>();

    public UsefulLinksCommand() {
        commands.add(COMMAND_USEFUL_LINKS);
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
        if (message.getText().equals(COMMAND_USEFUL_LINKS)) {
            return getSendMessageCallSchedule(message);
        }

        return null;
    }

    private SendMessage getSendMessageCallSchedule(final Message message) {
        return new SendMessage()
                .setChatId(message.getChatId())
                .setText("Выберите команду")
                .setReplyMarkup(getInlineKeyboardMarkupUsefulLinks());
    }

    private InlineKeyboardMarkup getInlineKeyboardMarkupUsefulLinks() {
        final InlineKeyboardMarkupBuilder inlineKeyboardMarkupBuilder = new InlineKeyboardMarkupBuilder();

        inlineKeyboardMarkupBuilder
                .row()
                .button(new InlineKeyboardButton(NAME_BUTTON_SITE).setUrl(URL_SITE))
                .button(new InlineKeyboardButton(NAME_BUTTON_PROVISIONS).setUrl(URL_PROVISIONS))
                .button(new InlineKeyboardButton(NAME_BUTTON_ARTICLE_ASSOCIATION).setUrl(URL_ARTICLE_ASSOCIATION))
                .endRow();

        return inlineKeyboardMarkupBuilder.build();
    }

}