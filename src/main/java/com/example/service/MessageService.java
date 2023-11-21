package com.example.service;

import com.example.command.Command;
import com.example.entity.User;
import com.example.exception.UnhandledCommandException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
@Slf4j
@RequiredArgsConstructor
public class MessageService {

    private final AbsSender absSender;

//    public void handleMessage() {
//
//    }
//
//    public void sendMessage(User user, String text) {
//        SendMessage message = SendMessage.builder()
//                .text(text)
//                .chatId(user.getChatId())
//                .build();
//
//        message.enableMarkdown(true);
//
//        log.info("Answer sent to user: " + user.prepareUserName());
//        try {
//            absSender.execute(message);
//        } catch (TelegramApiException e) {
//            log.error(e.getLocalizedMessage());
//        }
//    }

//    private Command getCommandByIdentifier(String identifier) throws UnhandledCommandException {
//        Command command = commands.get(identifier);
//        if (Objects.isNull(command)) {
//            throw new UnhandledCommandException("Command " + identifier + " is not supported");
//        }
//        return command;
//    }
}
