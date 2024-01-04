package com.livecurrency.bot.handler;

import com.livecurrency.entity.Request;
import com.livecurrency.exception.NotFoundException;
import com.livecurrency.service.RequestService;
import com.livecurrency.util.TelegramUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static com.livecurrency.util.TgUserToLiveUserMapper.mapToLiveUser;

@Slf4j
@Component
@RequiredArgsConstructor
public class SubscribeCurrencyHandler implements Handler {

    private final RequestService requestService;

    @Override
    public void handle(AbsSender sender, Message message) throws TelegramApiException {
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

        StringBuilder response = new StringBuilder();
        try {
            Request request = requestService.subscribe(mapToLiveUser(message.getFrom(), message.getChat()),
                    parsedMessage[0],
                    Double.valueOf(parsedMessage[1]));

            response.append(String.format("""
                    Prepared a subscription for currency %s.
                    You'll be immediately notified after the price is changed more than %.2f%%!
                    """, request.getCurrency().getSymbol(), request.getPercents()));
        } catch (NotFoundException ex) {
            response.append(ex.getMessage());
        } catch (NumberFormatException ex) {
            response.append("Invalid input: ")
                    .append(ex.getMessage());
        }


        sender.execute(TelegramUtil
                .buildMessage(response.toString(), message.getChatId()));
    }
}
