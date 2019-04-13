package ru.ras.economybot.telegram.keyboard;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

public interface IKeyboardBuilder<T, Keyboard> {

    T create();
    T row();
    T endRow();
    T button(final InlineKeyboardButton custom);
    T button(final String text);
    T button(final String text, final boolean contact);
    T button(final String text, final String callback);
    Keyboard build();

}