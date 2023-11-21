package com.example.command;

import com.example.entity.User;
import com.example.exception.UserNotFoundException;
import com.example.service.MessageService;
import com.example.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.bots.AbsSender;

import static com.example.command.CommandDetails.START;

@Component
@Slf4j
public class StartCommand extends Command {

    private final UserService userService;

    public StartCommand(UserService userService) {
        super(START.getCommandIdentifier(), START.getCommandDescription());
        this.userService = userService;
    }

    @Override
    public void execute(AbsSender sender, User user) {
        boolean isCreated = false;
        try {
            userService.getUserByUsername(user.getUserName());
        } catch (UserNotFoundException ex) {
            userService.registerUser(user);
            isCreated = true;
        }
        sendAnswer(sender, user, buildMessage(isCreated));
    }

    private String buildMessage(Boolean isCreated) {
        return (isCreated ?
                "Successfully registered to our service! " : "You're already registered! ") + "Ready to Work!";
    }
}
