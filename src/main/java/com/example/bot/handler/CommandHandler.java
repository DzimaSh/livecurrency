package com.example.bot.handler;

import com.example.command.CheckCurrencyCommand;
import com.example.command.Command;
import com.example.command.CommandDetails;
import com.example.command.SettingsCommand;
import com.example.command.StartCommand;
import com.example.command.SubscribeCurrencyCommand;
import com.example.entity.User;
import com.example.exception.UnhandledCommandException;
import com.example.util.TelegramUtil;
import com.example.util.TgUserToLiveUserMapper;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.HashMap;
import java.util.Objects;

@Component
@Slf4j
public class CommandHandler implements Handler {

    private final HashMap<CommandDetails, Command> commands = new HashMap<>();

    public CommandHandler(StartCommand startCommand, SettingsCommand settingsCommand,
                          CheckCurrencyCommand checkCurrencyCommand, SubscribeCurrencyCommand subscribeCurrencyCommand) {
        commands.put(CommandDetails.START, startCommand);
        commands.put(CommandDetails.SETTINGS, settingsCommand);
        commands.put(CommandDetails.CHECK_CURRENCY, checkCurrencyCommand);
        commands.put(CommandDetails.SUBSCRIBE_CURRENCY, subscribeCurrencyCommand);
    }

    @PostConstruct
    private void init() {
        if (commands.size() > 0) {
            log.info("Commands initialized");
            log.info("Handled commands list: " + commands.keySet());
        }
    }

    @Override
    public void handle(AbsSender sender, Message message) throws UnhandledCommandException {
        CommandDetails command = retrieveCommandFromMessage(message);
        User liveUser = TgUserToLiveUserMapper.mapToLiveUser(message.getFrom(), message.getChat());

        if (Objects.isNull(command)) {
            throw new UnhandledCommandException("Command " + message.getText() + " is not supported");
        }

        commands.get(command).execute(sender, liveUser);
    }

    private CommandDetails retrieveCommandFromMessage(Message message) throws UnhandledCommandException {
        try {
            return TelegramUtil.getCommandByIdentifier(message.getText());
        } catch (IllegalArgumentException ex) {
            throw new UnhandledCommandException("Command " + message.getText() + " is not supported");
        }
    }
}
