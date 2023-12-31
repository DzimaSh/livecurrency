package com.livecurrency.command;

import com.livecurrency.entity.User;
import com.livecurrency.exception.MaximumUsersReachedException;
import com.livecurrency.exception.NotFoundException;
import com.livecurrency.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.bots.AbsSender;

import static com.livecurrency.command.CommandDetails.START;

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
            user = userService.getUserByUsername(user.getUserName());
        } catch (NotFoundException ex) {
            try {
                user = userService.registerUser(user);
            } catch (MaximumUsersReachedException e) {
                log.info("Forbid to create user");
                sendAnswer(sender, user, buildErrorMessage());
                return;
            }
            log.info("User is created");
            isCreated = true;
        }
        sendAnswer(sender, user, buildMessage(isCreated));
    }

    private String buildMessage(Boolean isCreated) {
        return (isCreated ?
                "Successfully registered to our service! " : "You're already registered! ") + "Ready to Work!";
    }

    private String buildErrorMessage() {
        return "Unfortunately, you cannot be registered. Apologize for that!";
    }
}
