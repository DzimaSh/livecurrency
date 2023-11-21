package com.example.bot;

import com.example.bot.handler.CommandHandler;
import com.example.bot.handler.Handler;
import com.example.exception.UnhandledException;
import com.example.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;

@Slf4j
@Component
public class LiveCurrencyBot extends TelegramLongPollingBot {

    private final Constants constants;
    private final HashMap<String, Handler> handlerList = new HashMap<>();

    public LiveCurrencyBot(Constants constants, CommandHandler commandHandler) {
        super(constants.getBotToken());
        this.constants = constants;

        handlerList.put(Constants.COMMAND_KEY, commandHandler);
    }


    @Override
    public String getBotUsername() {
        return constants.getBotUsername();
    }

    @Override
    public void onUpdateReceived(Update update) {
        log.debug("Message received");

        if (update.getMessage().getText().startsWith(Constants.COMMAND_PREFIX)) {
            try {
                handlerList.get(Constants.COMMAND_KEY).handle(this, update.getMessage());
            } catch (UnhandledException e) {
                log.error(e.getMessage());
            }
        }
    }
}
