package ru.ras.economybot.telegram.utils;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import ru.ras.economybot.telegram.commands.CallScheduleCommand;
import ru.ras.economybot.telegram.commands.ClassScheduleCommand;
import ru.ras.economybot.telegram.commands.ContactsCommand;
import ru.ras.economybot.telegram.commands.UsefulLinksCommand;

import java.util.ArrayList;
import java.util.List;

public class KeyboardUtils {

    public static ReplyKeyboardMarkup getReplyKeyboardMarkupMain() {
        final ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        final List<KeyboardRow> keyboardRowList = new ArrayList<>();

        final KeyboardRow keyboardRowFirst = new KeyboardRow();
        keyboardRowFirst.add(CallScheduleCommand.COMMAND_CALL_SCHEDULE);
        keyboardRowFirst.add(ClassScheduleCommand.COMMAND_CLASS_SCHEDULE);

        final KeyboardRow keyboardRowSecond = new KeyboardRow();
        keyboardRowSecond.add(ContactsCommand.COMMAND_CONTACTS);
        keyboardRowSecond.add(UsefulLinksCommand.COMMAND_USEFUL_LINKS);

        keyboardRowList.add(keyboardRowFirst);
        keyboardRowList.add(keyboardRowSecond);

        replyKeyboardMarkup.setKeyboard(keyboardRowList);

        return replyKeyboardMarkup;
    }

}