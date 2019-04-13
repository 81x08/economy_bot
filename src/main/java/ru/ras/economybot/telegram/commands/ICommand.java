package ru.ras.economybot.telegram.commands;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.Set;

public interface ICommand {

    Set<String> getCallbacks();

    Set<String> getCommands();

    BotApiMethod execute(final AbsSender absSender, final Update update, final CallbackQuery callbackQuery);
    BotApiMethod execute(final AbsSender absSender, final Update update, final Message message);

}