package com.example.bot.handler;

import com.example.command.Command;
import com.example.command.CommandDetails;
import com.example.command.SettingsCommand;
import com.example.command.StartCommand;
import com.example.entity.User;
import com.example.exception.UnhandledCommandException;
import com.example.service.UserService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.HashMap;

@Component
@Slf4j
public class CommandHandler implements Handler {

    private final HashMap<CommandDetails, Command> commands = new HashMap<>();

    public CommandHandler(StartCommand startCommand, SettingsCommand settingsCommand) {
        commands.put(CommandDetails.START, startCommand);
        commands.put(CommandDetails.SETTINGS, settingsCommand);
    }

    @PostConstruct
    private void init() {
        if (commands.size() > 0)
            log.info("Commands initialized");
    }

    @Override
    public void handle(AbsSender sender, Update update) throws UnhandledCommandException {
        CommandDetails command = retrieveCommandFromUpdate(update);
        switch (command) {
            case START -> new StartCommand().execute(sender, new User());
            case SETTINGS -> new SettingsCommand().execute(sender, new User());
        }
    }

    private CommandDetails retrieveCommandFromUpdate(Update update) throws UnhandledCommandException {
        Message message = update.getMessage();
        try {
            return Command.getByIdentifier(message.getText());
        } catch (IllegalArgumentException ex) {
            throw new UnhandledCommandException("Command " + message.getText() + " is not supported");
        }
    }
}
