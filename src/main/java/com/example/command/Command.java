package com.example.command;

import com.example.entity.User;
import com.example.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Locale;

@Slf4j
public abstract class Command extends BotCommand {
    protected Command(String identifier, String description) {
        super(identifier, description);
    }

    protected void sendAnswer(AbsSender absSender, User user, String text) {
        SendMessage message = SendMessage.builder()
                .text(text)
                .chatId(user.getChatId())
                .build();

        message.enableMarkdown(true);

        log.info("Answer sent to user: " + user.prepareUserName());
        try {
            absSender.execute(message);
        } catch (TelegramApiException e) {
            log.error(e.getLocalizedMessage());
        }
    }

    public static CommandDetails getByIdentifier(String identifier) {
        String[] options = identifier.split(" ");
        if (options.length > 0) {
            return CommandDetails.valueOf(options[0]
                    .toUpperCase(Locale.ROOT)
                    .replace(Constants.COMMAND_PREFIX, ""));
        }
        return null;
    }

   public abstract void execute(AbsSender sender, User user);
}
