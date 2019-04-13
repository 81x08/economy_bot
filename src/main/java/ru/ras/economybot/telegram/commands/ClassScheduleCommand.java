package ru.ras.economybot.telegram.commands;

import javafx.util.Pair;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import ru.ras.economybot.telegram.directions.FactoryDirections;
import ru.ras.economybot.telegram.images.ClassSchedulesImages;
import ru.ras.economybot.telegram.keyboard.InlineKeyboardMarkupBuilder;

import java.io.File;
import java.util.*;

public class ClassScheduleCommand implements ICommand {

    public static final String COMMAND_CLASS_SCHEDULE = "Расписание занятий";

    private static final Logger logger = LoggerFactory.getLogger(ClassScheduleCommand.class);

    private static final String NAME_BUTTON_DIRECTION_GROUP_BACK = "Назад";

    private static final String CALLBACK_DIRECTION_GROUP_BACK = "callback_direction_group_back";
    private static final String CALLBACK_DIRECTION_GROUP_PREFIX = "callback_direction_group_";
    private static final String CALLBACK_DIRECTION_PREFIX = "callback_direction_";

    private static final Set<String> callbacks = new HashSet<>();
    private static final Set<String> commands = new HashSet<>();

    public ClassScheduleCommand() {
        createCallbacks();

        callbacks.add(CALLBACK_DIRECTION_GROUP_BACK);

        commands.add(COMMAND_CLASS_SCHEDULE);
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

        if (data.equals(CALLBACK_DIRECTION_GROUP_BACK)) {
            return getSendMessageChooseDirection(absSender, callbackQuery);
        }

        if (data.startsWith(CALLBACK_DIRECTION_GROUP_PREFIX)) {
            return getSendMessageClassSchedule(absSender, callbackQuery);
        }

        if (data.startsWith(CALLBACK_DIRECTION_PREFIX)) {
            return getSendMessageChooseGroup(absSender, callbackQuery);
        }

        return null;
    }

    @Override
    public BotApiMethod execute(final AbsSender absSender, final Update update, final Message message) {
        if (message.getText().equals(COMMAND_CLASS_SCHEDULE)) {
            return getSendMessageChooseDirection(message);
        }

        return null;
    }

    private AnswerCallbackQuery getSendMessageChooseGroup(final AbsSender absSender, final CallbackQuery callbackQuery) {
        final String data = callbackQuery.getData();

        final int findLastUnderline = data.lastIndexOf("_") + 1;

        final String direction = data.substring(findLastUnderline, data.length());

        final Pair<String, String> pairDirection = FactoryDirections.getInstance().getPairDirection(direction);

        final StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("Направление - ").append(pairDirection.getKey()).append("\n").append("Выберите группу");

        try {
            absSender.execute(
                    new EditMessageText()
                            .setChatId(callbackQuery.getMessage().getChatId())
                            .setMessageId(callbackQuery.getMessage().getMessageId())
                            .setText(stringBuilder.toString())
                            .setReplyMarkup(getInlineKeyboardMarkupGroups(direction))
            );
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

        return new AnswerCallbackQuery().setCallbackQueryId(callbackQuery.getId());
    }

    private AnswerCallbackQuery getSendMessageClassSchedule(final AbsSender absSender, final CallbackQuery callbackQuery) {
        final String data = callbackQuery.getData();

        final int findLastUnderline = data.lastIndexOf("_") + 1;

        final String groupId = data.substring(findLastUnderline, data.length());

        final Set<String> imagesSchedulers = ClassSchedulesImages.getImages().get(groupId);

        for (String image : imagesSchedulers) {
            try {
                absSender.execute(
                        new SendPhoto()
                                .setChatId(callbackQuery.getMessage().getChatId())
                                .setPhoto(new File(image))
                );
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }

        return new AnswerCallbackQuery().setCallbackQueryId(callbackQuery.getId());
    }

    private SendMessage getSendMessageChooseDirection(final Message message) {
        return new SendMessage()
                .setChatId(message.getChatId())
                .setText("Выберите направление")
                .setReplyMarkup(getInlineKeyboardMarkupDirections());
    }

    private AnswerCallbackQuery getSendMessageChooseDirection(final AbsSender absSender, final CallbackQuery callbackQuery) {
        try {
            absSender.execute(
                    new EditMessageText()
                            .setChatId(callbackQuery.getMessage().getChatId())
                            .setMessageId(callbackQuery.getMessage().getMessageId())
                            .setText("Выберите направление")
                            .setReplyMarkup(getInlineKeyboardMarkupDirections())
            );
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

        return new AnswerCallbackQuery().setCallbackQueryId(callbackQuery.getId());
    }

    private InlineKeyboardMarkup getInlineKeyboardMarkupDirections() {
        final InlineKeyboardMarkupBuilder inlineKeyboardMarkupBuilder = new InlineKeyboardMarkupBuilder();

        inlineKeyboardMarkupBuilder.row();

        final Set<Pair<String, String>> directionsPairs = FactoryDirections.getInstance().getDirections();

        int counterButton = 0;

        for (Pair<String, String> pair : directionsPairs) {
            counterButton++;

            inlineKeyboardMarkupBuilder.button(
                    pair.getKey(),
                    CALLBACK_DIRECTION_PREFIX + pair.getValue()
            );

            if (counterButton == 3) {
                counterButton = 0;

                inlineKeyboardMarkupBuilder.endRow();
                inlineKeyboardMarkupBuilder.row();
            }
        }

        inlineKeyboardMarkupBuilder.endRow();

        return inlineKeyboardMarkupBuilder.build();
    }

    private InlineKeyboardMarkup getInlineKeyboardMarkupGroups(final String direction) {
        final InlineKeyboardMarkupBuilder inlineKeyboardMarkupBuilder = new InlineKeyboardMarkupBuilder();

        inlineKeyboardMarkupBuilder.row();

        final Pair<String, String> pairDirection = FactoryDirections.getInstance().getPairDirection(direction);
        final Set<String> groupsDirections = FactoryDirections.getInstance().getGroups(pairDirection);

        int counterButton = 0;

        for (String groupId : groupsDirections) {
            counterButton++;

            inlineKeyboardMarkupBuilder.button(
                    groupId,
                    CALLBACK_DIRECTION_GROUP_PREFIX + groupId
            );

            if (counterButton == 5) {
                counterButton = 0;

                inlineKeyboardMarkupBuilder.endRow();
                inlineKeyboardMarkupBuilder.row();
            }
        }

        inlineKeyboardMarkupBuilder
                .endRow()
                .row()
                .button(NAME_BUTTON_DIRECTION_GROUP_BACK, CALLBACK_DIRECTION_GROUP_BACK)
                .endRow();

        return inlineKeyboardMarkupBuilder.build();
    }

    private void createCallbacks() {
        final Set<Pair<String, String>> directionsPairs = FactoryDirections.getInstance().getDirections();

        for (Pair<String, String> direction : directionsPairs) {
            callbacks.add(CALLBACK_DIRECTION_PREFIX + direction.getValue());

            final Set<String> groups = FactoryDirections.getInstance().getGroups(direction);

            for (String groupId : groups) {
                callbacks.add(CALLBACK_DIRECTION_GROUP_PREFIX + groupId);
            }
        }
    }

}