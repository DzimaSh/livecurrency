package com.livecurrency.bot;

import com.livecurrency.bot.handler.CommandHandler;
import com.livecurrency.bot.handler.GetCurrencyPriceHandler;
import com.livecurrency.bot.handler.Handler;
import com.livecurrency.bot.handler.SubscribeCurrencyHandler;
import com.livecurrency.command.CommandDetails;
import com.livecurrency.exception.UnhandledException;
import com.livecurrency.util.BotProperties;
import com.livecurrency.util.TelegramUtil;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import static com.livecurrency.bot.util.BotActionConstants.COMMAND_KEY;
import static com.livecurrency.bot.util.BotActionConstants.COMMAND_PREFIX;
import static com.livecurrency.bot.util.BotActionConstants.CURRENCY_PRICE_MESSAGE_KEY;
import static com.livecurrency.bot.util.BotActionConstants.CURRENCY_SUBSCRIBE_MESSAGE_KEY;

@Slf4j
@Component
public class LiveCurrencyBot extends TelegramLongPollingBot {

    private final BotProperties botProperties;
    private final Map<String, Handler> handlerList = new HashMap<>();
    private final Map<Long, Status> chatStatus = new ConcurrentHashMap<>();

    public LiveCurrencyBot(BotProperties botProperties,
                           CommandHandler commandHandler, GetCurrencyPriceHandler currencyPriceHandler,
                           SubscribeCurrencyHandler subscribeCurrencyHandler) {
        super(botProperties.getBotToken());
        this.botProperties = botProperties;

        handlerList.put(COMMAND_KEY, commandHandler);
        handlerList.put(CURRENCY_PRICE_MESSAGE_KEY, currencyPriceHandler);
        handlerList.put(CURRENCY_SUBSCRIBE_MESSAGE_KEY, subscribeCurrencyHandler);
    }


    @Override
    public String getBotUsername() {
        return botProperties.getBotUsername();
    }

    @Override
    public void onUpdateReceived(Update update) {
        log.debug("Message received");

        try {
            final Message message = Objects.requireNonNull(update.getMessage());
            final Long chatId = message.getChatId();

            if (Objects.isNull(chatStatus.get(chatId))) {
                chatStatus.put(chatId, Status.WAIT_FOR_COMMAND);
            }

            final String action = retrieveActionFromMessage(message, chatId);

            if (Objects.isNull(action)) {
                this.execute(TelegramUtil
                        .buildMessage("Unsupported request! Try `/help` for further info", chatId));
            } else {
                handlerList.get(action).handle(this, message);
                updateChatStatus(action, message, chatId);
            }

        } catch (TelegramApiException | NullPointerException e) {
            log.error("External Telegram exception!");
        } catch (UnhandledException e) {
            log.error("Unhandled message received!");
        }
    }

    private String retrieveActionFromMessage(@NonNull Message message, Long chatId) throws TelegramApiException {
        if (message.getText().startsWith(COMMAND_PREFIX)) {
            return COMMAND_KEY;
        } else if (Status.WAIT_FOR_CURRENCY_SYMBOL.equals(chatStatus.get(chatId))) {
            return CURRENCY_PRICE_MESSAGE_KEY;
        } else if (Status.WAIT_FOR_CURRENCY_SUBSCRIBE.equals(chatStatus.get(chatId))) {
            return CURRENCY_SUBSCRIBE_MESSAGE_KEY;
        }
        return null;
    }

    private void updateChatStatus(String action, Message message, Long chatId) {
        if (action.contains(COMMAND_KEY)) {
            final CommandDetails command = TelegramUtil.getCommandByIdentifier(message.getText());
            switch (Objects.requireNonNull(command)) {
                case CHECK_CURRENCY -> chatStatus.put(chatId, Status.WAIT_FOR_CURRENCY_SYMBOL);
                case SUBSCRIBE_CURRENCY -> chatStatus.put(chatId, Status.WAIT_FOR_CURRENCY_SUBSCRIBE);
                default -> chatStatus.put(chatId, Status.WAIT_FOR_COMMAND);
            }
        } else {
            chatStatus.put(chatId, Status.WAIT_FOR_COMMAND);
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
