package com.example.command;

import com.example.entity.User;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class SettingsCommand extends Command {

    private final static String command = "/settings";
    private final static String description = "Settings";

    public SettingsCommand() {
        super(command, description);
    }

    public void execute(AbsSender absSender, User user, Chat chat) {
        String userName = (user.getUserName() != null) ? user.getUserName() :
                String.format("%s %s", user.getLastName(), user.getFirstName());

        sendAnswer(absSender, chat.getId(), command, userName, buildMessage(user));
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
