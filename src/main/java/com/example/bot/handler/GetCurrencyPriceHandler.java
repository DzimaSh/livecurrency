package com.example.bot.handler;

import com.example.exception.UnhandledException;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

@Component
public class MessageHandler implements Handler {
    @Override
    public void handle(AbsSender sender, Message message) throws UnhandledException {

    }
}
