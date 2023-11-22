package com.example.bot.handler;

import com.example.entity.Currency;
import com.example.exception.UnhandledException;
import com.example.service.CurrencyService;
import com.example.util.TelegramUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@Component
@RequiredArgsConstructor
public class GetCurrencyPriceHandler implements Handler {

    private final CurrencyService currencyService;

    @Override
    public void handle(AbsSender sender, Message message) throws TelegramApiException {
        String[] parsedMessage = message.getText().trim().split(" ");
        if (parsedMessage.length == 0) {
            sender.execute(TelegramUtil
                    .buildMessage("Abort! No symbols provided!", message.getChatId()));
            return;
        } else if (parsedMessage.length > 1) {
            sender.execute(TelegramUtil
                    .buildMessage(String.format("Invalid request! Trying to fetch %s currency ", parsedMessage[0]), message.getChatId()));
        } else {
            sender.execute(TelegramUtil
                    .buildMessage(String.format("Fetching %s currency", parsedMessage[0]), message.getChatId()));
        }

        Currency currency = fetchCurrency(parsedMessage[0]);
        sender.execute(TelegramUtil
                .buildMessage(String.format(
                            """
                            Symbol: %s
                            Price: %.20f
                            TimeStamp: %s
                            """, currency.getSymbol(), currency.getPrice(), currency.getCreationDate()),
                        message.getChatId()));
    }

    private Currency fetchCurrency(String symbol) {
        return currencyService.updatePrice(symbol);
    }
}
