package com.example.bot;

import com.example.command.CommandEnum;
import com.example.command.SettingsCommand;
import com.example.command.StartCommand;
import com.example.entity.User;
import com.example.exception.UnhandledCommandException;
import com.example.service.UserService;
import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.units.qual.A;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;

@Component
@RequiredArgsConstructor
public class CommandHandler implements Handler {

    private final UserService userService;

    @Override
    public void handle(AbsSender sender, Update update) throws UnhandledCommandException {
        CommandEnum command = retrieveCommandFromUpdate(update);
        switch (command) {
            case START -> new StartCommand().execute(sender, new User());
            case SETTINGS -> new SettingsCommand().execute(sender, new User());
        }
    }

    private CommandEnum retrieveCommandFromUpdate(Update update) throws UnhandledCommandException {
        Message message = update.getMessage();
        try {
            return CommandEnum.getByIdentifier(message.getText());
        } catch (IllegalArgumentException ex) {
            throw new UnhandledCommandException("Command " + message.getText() + " is not supported");
        }
    }
}
