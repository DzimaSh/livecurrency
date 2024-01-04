package com.livecurrency.bot.handler;

import com.livecurrency.command.CheckCurrencyCommand;
import com.livecurrency.command.Command;
import com.livecurrency.command.CommandDetails;
import com.livecurrency.command.SettingsCommand;
import com.livecurrency.command.StartCommand;
import com.livecurrency.command.SubscribeCurrencyCommand;
import com.livecurrency.entity.User;
import com.livecurrency.exception.UnhandledCommandException;
import com.livecurrency.util.TelegramUtil;
import com.livecurrency.util.TgUserToLiveUserMapper;
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
        if (!commands.isEmpty()) {
            log.info("Commands initialized! Handled commands list: " + commands.keySet());
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
