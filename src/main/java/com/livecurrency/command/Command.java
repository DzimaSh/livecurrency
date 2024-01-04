package com.livecurrency.command;

import com.livecurrency.entity.User;
import com.livecurrency.util.TelegramUtil;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
public abstract class Command extends BotCommand {
    protected Command(String identifier, String description) {
        super(identifier, description);
    }

    protected void sendAnswer(AbsSender absSender, User user, String text) {
        final SendMessage message = TelegramUtil.buildMessage(text, user.getChatId());

        log.info("Answer sent to user: " + user.prepareUserName());
        try {
            absSender.execute(message);
        } catch (TelegramApiException e) {
            log.error(e.getLocalizedMessage());
        }
    }

   public abstract void execute(AbsSender sender, User user);
}
