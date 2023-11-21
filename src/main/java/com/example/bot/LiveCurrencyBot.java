package com.example.bot;

import com.example.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@Component
public class LiveCurrencyBot extends TelegramLongPollingBot {

    private final Constants constants;

    public LiveCurrencyBot(Constants constants) {
        super(constants.getBotToken());
        this.constants = constants;
    }

    @Override
    public String getBotUsername() {
        return constants.getBotUsername();
    }

    @Override
    public void onUpdateReceived(Update update) {
        log.info("Message received");
    }
}
