package com.example.bot;

import com.example.bot.handler.CommandHandler;
import com.example.bot.handler.Handler;
import com.example.bot.handler.GetCurrencyPriceHandler;
import com.example.command.CommandDetails;
import com.example.exception.UnhandledException;
import com.example.util.Constants;
import com.example.util.TelegramUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class LiveCurrencyBot extends TelegramLongPollingBot {

    private final Constants constants;
    private final Map<String, Handler> handlerList = new HashMap<>();
    private final Map<Long, Status> chatStatus = new ConcurrentHashMap<>();

    public LiveCurrencyBot(Constants constants,
                           CommandHandler commandHandler, GetCurrencyPriceHandler messageHandler) {
        super(constants.getBotToken());
        this.constants = constants;

        handlerList.put(Constants.COMMAND_KEY, commandHandler);
        handlerList.put(Constants.MESSAGE_KEY, messageHandler);
    }


    @Override
    public String getBotUsername() {
        return constants.getBotUsername();
    }

    @Override
    public void onUpdateReceived(Update update) {
        log.debug("Message received");

        try {
            Long chatId = update.getMessage().getChatId();

            if (Objects.isNull(chatStatus.get(chatId))) {
                chatStatus.put(chatId, Status.WAIT_FOR_COMMAND);
            }

            if (update.getMessage().getText().startsWith(Constants.COMMAND_PREFIX)) {
                CommandDetails command = TelegramUtil.getCommandByIdentifier(update.getMessage().getText());
                switch (Objects.requireNonNull(command)) {
                    case CHECK_CURRENCY -> chatStatus.put(chatId, Status.WAIT_FOR_CURRENCY_SYMBOL);
                    case SUBSCRIBE_CURRENCY -> chatStatus.put(chatId, Status.WAIT_FOR_CURRENCY_SUBSCRIBE);
                }
                handlerList.get(Constants.COMMAND_KEY).handle(this, update.getMessage());

                return;
            } else if (chatStatus.get(chatId).equals(Status.WAIT_FOR_CURRENCY_SYMBOL)) {
                handlerList.get(Constants.MESSAGE_KEY).handle(this, update.getMessage());
            } else {
                this.execute(TelegramUtil
                        .buildMessage("Unsupported request! Try `/help` for further info", chatId));
            }
            chatStatus.put(chatId, Status.WAIT_FOR_COMMAND);
        } catch (TelegramApiException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        } catch (UnhandledException e) {
            log.error(e.getMessage());
        }
    }

    @AllArgsConstructor
    private enum Status {
        WAIT_FOR_COMMAND,
        WAIT_FOR_CURRENCY_SYMBOL,
        WAIT_FOR_CURRENCY_SUBSCRIBE,
        WAIT_FOR_SETTINGS
    }
}
