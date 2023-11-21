package com.example.bot;

import com.example.command.Command;
import com.example.command.SettingsCommand;
import com.example.command.StartCommand;
import com.example.exception.UnhandledException;
import com.example.util.Constants;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Component
public class LiveCurrencyBot extends TelegramLongPollingBot {

    private final Constants constants;
    private final HashMap<String, Handler> handlerList = new HashMap<>();
    private final HashMap<String, Command> commands = new HashMap<>();

    public LiveCurrencyBot(Constants constants, CommandHandler commandHandler) {
        super(constants.getBotToken());
        this.constants = constants;
        handlerList.put("command", commandHandler);
    }

    @PostConstruct
    private void init() {
        SettingsCommand settingsCommand = new SettingsCommand();
        commands.put(settingsCommand.getCommand(), settingsCommand);

        StartCommand startCommand = new StartCommand();
        commands.put(startCommand.getCommand(), startCommand);

        log.info("Commands initialized");
    }


    @Override
    public String getBotUsername() {
        return constants.getBotUsername();
    }

    @Override
    public void onUpdateReceived(Update update) {
        log.debug("Message received");

        if (update.getMessage().getText().startsWith("/")) {
            try {
                handlerList.get("command").handle(this, update);
            } catch (UnhandledException e) {
                log.error(e.getMessage());
            }
        }
    }
}
