package com.example.command;

import com.example.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
public abstract class Command extends BotCommand {
    protected Command(String identifier, String description) {
        super(identifier, description);
    }

    protected void sendAnswer(AbsSender absSender, Long chatId, String commandName, String userName, String text) {
        SendMessage message = new SendMessage();

        message.enableMarkdown(true);
        message.setChatId(chatId.toString());
        message.setText(text);
        log.info("Answer sent to user: " + userName);
        try {
            absSender.execute(message);
        } catch (TelegramApiException e) {
            log.error(e.getLocalizedMessage());
        }
    }

   public abstract void execute(AbsSender absSender, User user, Chat chat);
}
