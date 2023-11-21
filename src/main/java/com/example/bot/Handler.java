package com.example.bot;

import com.example.exception.UnhandledException;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;

@FunctionalInterface
public interface Handler {
    void handle(AbsSender sender, Update update) throws UnhandledException;
}
