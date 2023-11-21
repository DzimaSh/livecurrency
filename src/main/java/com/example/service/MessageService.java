package com.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.bots.AbsSender;

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
