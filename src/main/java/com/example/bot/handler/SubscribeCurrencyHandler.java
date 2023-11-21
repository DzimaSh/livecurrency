package com.example.bot.handler;

import com.example.exception.UnhandledException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@Component
public class SubscribeCurrencyHandler implements Handler {
    @Override
    public void handle(AbsSender sender, Message message) throws UnhandledException, TelegramApiException {

    }
}
