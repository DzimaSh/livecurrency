package com.livecurrency.bot.handler;

import com.livecurrency.exception.UnhandledException;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@FunctionalInterface
public interface Handler {
    void handle(AbsSender sender, Message message) throws UnhandledException, TelegramApiException;
}
