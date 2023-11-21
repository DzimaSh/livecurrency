package com.example.bot.handler;

import com.example.exception.UnhandledException;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

@FunctionalInterface
public interface Handler {
    void handle(AbsSender sender, Message message) throws UnhandledException;
}
