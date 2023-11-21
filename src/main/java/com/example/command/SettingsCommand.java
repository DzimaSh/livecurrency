package com.example.command;

import com.example.entity.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

import static com.example.command.CommandEnum.SETTINGS;

public class SettingsCommand extends Command {
    public SettingsCommand() {
        super(SETTINGS.getCommandIdentifier(), SETTINGS.getCommandDescription());
    }

    @Override
    public void execute(AbsSender sender, User user) {
        sendAnswer(sender, user, buildMessage(user));
    }

    private String buildMessage(User user) {
        StringBuilder message = new StringBuilder("Current settings:\n");
        user.getRequests()
                .forEach((request -> {
                    message.append(String
                            .format("""
                                        Currency: %s
                                        Percentage changed to notify: %.2f
                                        Start time: %s
                                        
                                    """, request.getCurrency(), request.getPercents(), request.getTimeToStart().getTime()));
                }));
        message.append("You can adjust them");

        return message.toString();
    }
}
