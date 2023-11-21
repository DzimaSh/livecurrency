package com.example.bot.handler;

import com.example.exception.UnhandledException;
import com.example.service.RequestService;
import com.example.util.TelegramUtil;
import com.example.util.TgUserToLiveUserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static com.example.util.TgUserToLiveUserMapper.mapToLiveUser;

@Slf4j
@Component
@RequiredArgsConstructor
public class SubscribeCurrencyHandler implements Handler {

    private final RequestService requestService;

    @Override
    public void handle(AbsSender sender, Message message) throws UnhandledException, TelegramApiException {
        String[] parsedMessage = message.getText().trim().split(" ");
        if (parsedMessage.length == 0) {
            sender.execute(TelegramUtil
                    .buildMessage("Abort! No symbols provided!", message.getChatId()));
            return;
        } else if (parsedMessage.length > 2) {
            sender.execute(TelegramUtil
                    .buildMessage(String.format("Invalid request! Trying to subscribe %s currency with percentage %s%%",
                            parsedMessage[0], parsedMessage[1]),
                            message.getChatId()));
        } else {
            sender.execute(TelegramUtil
                    .buildMessage(String.format("Subscribe %s currency with percentage %s%%",
                            parsedMessage[0], parsedMessage[1]),
                            message.getChatId()));
        }

        requestService.subscribe(mapToLiveUser(message.getFrom(), message.getChat()),
                parsedMessage[0],
                Double.valueOf(parsedMessage[1]));
    }
}
