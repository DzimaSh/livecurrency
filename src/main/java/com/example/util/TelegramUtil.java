package com.example.util;

import com.example.command.CommandDetails;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.Locale;

import static com.example.bot.util.BotActionConstants.COMMAND_PREFIX;

public class TelegramUtil {
    public static SendMessage buildMessage(String text, Long chatId) {
        SendMessage message = SendMessage.builder()
                .chatId(chatId)
                .text(text)
                .build();
        message.enableMarkdown(true);
        return message;
    }

    public static CommandDetails getCommandByIdentifier(String identifier) {
        String[] options = identifier.split(" ");
        if (options.length > 0) {
            return CommandDetails.valueOf(options[0]
                    .toUpperCase(Locale.ROOT)
                    .replace(COMMAND_PREFIX, ""));
        }
        return null;
    }
}
