package ru.ras.economybot.telegram.commands;

import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import ru.ras.economybot.telegram.images.ContactsImages;
import ru.ras.economybot.telegram.keyboard.InlineKeyboardMarkupBuilder;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class ContactsCommand implements ICommand {

    public static final String COMMAND_CONTACTS = "Контакты";

    public static final String NAME_BUTTON_GMU = "ГМУ";
    public static final String NAME_BUTTON_FINANCE = "Финансы";
    public static final String NAME_BUTTON_ECONOMY = "Экономика";
    public static final String NAME_BUTTON_MANAGEMENT = "Менеджмент";
    public static final String NAME_BUTTON_DIRECTORATE = "Директорат";

    public static final String CALLBACK_CONTACTS_GMU = "callback_contacts_gmu";
    public static final String CALLBACK_CONTACTS_FINANCE = "callback_contacts_finance";
    public static final String CALLBACK_CONTACTS_ECONOMY = "callback_contacts_economy";
    public static final String CALLBACK_CONTACTS_MANAGEMENT = "callback_contacts_management";
    public static final String CALLBACK_CONTACTS_DIRECTORATE = "callback_contacts_directorate";

    private static final Set<String> callbacks = new HashSet<>();
    private static final Set<String> commands = new HashSet<>();

    public ContactsCommand() {
        callbacks.add(CALLBACK_CONTACTS_GMU);
        callbacks.add(CALLBACK_CONTACTS_FINANCE);
        callbacks.add(CALLBACK_CONTACTS_ECONOMY);
        callbacks.add(CALLBACK_CONTACTS_MANAGEMENT);
        callbacks.add(CALLBACK_CONTACTS_DIRECTORATE);

        commands.add(COMMAND_CONTACTS);
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
        final String data = callbackQuery.getData();

        switch (data)   {
            case CALLBACK_CONTACTS_GMU:
            case CALLBACK_CONTACTS_FINANCE:
            case CALLBACK_CONTACTS_ECONOMY:
            case CALLBACK_CONTACTS_MANAGEMENT:
            case CALLBACK_CONTACTS_DIRECTORATE: {
                return getSendMessageContactsImages(absSender, callbackQuery);
            }
        }

        return null;
    }

    @Override
    public BotApiMethod execute(final AbsSender absSender, final Update update, final Message message) {
        if (message.getText().equals(COMMAND_CONTACTS)) {
            return getSendMessageChooseDirection(message);
        }

        return null;
    }

    private AnswerCallbackQuery getSendMessageContactsImages(final AbsSender absSender, final CallbackQuery callbackQuery) {
        final String data = callbackQuery.getData();

        final int index = data.lastIndexOf("_") + 1;

        final String direction = data.substring(index);

        final String image = ContactsImages.getImages().get(direction);

        try {
            absSender.execute(
                    new SendPhoto()
                            .setChatId(callbackQuery.getMessage().getChatId())
                            .setPhoto(new File(image))
            );
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

        return new AnswerCallbackQuery().setCallbackQueryId(callbackQuery.getId());
    }

    private SendMessage getSendMessageChooseDirection(final Message message) {
        return new SendMessage()
                .setChatId(message.getChatId())
                .setText("Выберите отдел")
                .setReplyMarkup(getInlineKeyboardMarkupDirections());
    }

    private InlineKeyboardMarkup getInlineKeyboardMarkupDirections() {
        final InlineKeyboardMarkupBuilder inlineKeyboardMarkupBuilder = new InlineKeyboardMarkupBuilder();

        inlineKeyboardMarkupBuilder
                .row()
                .button(NAME_BUTTON_GMU, CALLBACK_CONTACTS_GMU)
                .button(NAME_BUTTON_FINANCE, CALLBACK_CONTACTS_FINANCE)
                .button(NAME_BUTTON_ECONOMY, CALLBACK_CONTACTS_ECONOMY)
                .endRow()
                .row()
                .button(NAME_BUTTON_MANAGEMENT, CALLBACK_CONTACTS_MANAGEMENT)
                .button(NAME_BUTTON_DIRECTORATE, CALLBACK_CONTACTS_DIRECTORATE)
                .endRow();

        return inlineKeyboardMarkupBuilder.build();
    }

}