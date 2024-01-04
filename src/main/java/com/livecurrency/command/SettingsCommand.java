package com.livecurrency.command;

import com.livecurrency.entity.User;
import com.livecurrency.exception.NotFoundException;
import com.livecurrency.service.UserService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.Objects;

import static com.livecurrency.command.CommandDetails.SETTINGS;

@Component
public class SettingsCommand extends Command {

    private final UserService userService;

    public SettingsCommand(UserService userService) {
        super(SETTINGS.getCommandIdentifier(), SETTINGS.getCommandDescription());
        this.userService = userService;
    }

    @Override
    public void execute(AbsSender sender, User user) {
        try {
            User liveUser = userService.getUserByUsername(user.getUserName());
            sendAnswer(sender, user, buildMessage(liveUser));
        } catch (NotFoundException ex) {
            sendAnswer(sender, user, "Unauthorized! Try to execute `/start`...");
        }
    }

    private String buildMessage(User user) {
        final StringBuilder message = new StringBuilder("Current settings:\n");
        if (!Objects.isNull(user.getRequests()) && !user.getRequests().isEmpty()) {
            user.getRequests()
                    .forEach((request -> {
                        message.append(String.format(
                                            """
                                                Currency: %s
                                                Percentage changed to notify: %.2f
                                        
                                            """,
                                        request.getCurrency(), request.getPercents()
                                ));
                    }));
            message.append("You can adjust them!");
        } else {
            message.append(
                            """
                            You have no any active observers.
                            Please execute `/subscribe_currency` to proceed.
                            """
            );
        }
        return message.toString();
    }
}
