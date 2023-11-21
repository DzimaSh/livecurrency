package com.example.bot;

import com.example.command.Command;
import com.example.command.SettingsCommand;
import com.example.service.MessageService;
import com.example.util.Constants;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;

@Slf4j
@Component
public class LiveCurrencyBot extends TelegramLongPollingBot {

    private final Constants constants;
    private final HashMap<String, Command> commands;
    private final MessageService handler;

    public LiveCurrencyBot(Constants constants) {
        super(constants.getBotToken());
        this.constants = constants;
        this.commands = new HashMap<>();
        this.handler = new MessageService(commands);
    }

    @PostConstruct
    private void init() {
        SettingsCommand settingsCommand = new SettingsCommand();
        commands.put(settingsCommand.getCommand(), settingsCommand);
        log.info("Commands initialized");
    }


    @Override
    public String getBotUsername() {
        return constants.getBotUsername();
    }

    @Override
    public void onUpdateReceived(Update update) {
        log.debug("Message received");

    }
}
