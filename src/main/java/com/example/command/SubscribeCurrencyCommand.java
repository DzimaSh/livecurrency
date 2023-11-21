package com.example.command;

import com.example.entity.User;
import com.example.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.bots.AbsSender;

import static com.example.command.CommandDetails.SUBSCRIBE_CURRENCY;

@Slf4j
@Component
public class SubscribeCurrencyCommand extends Command {

    private final UserService userService;

    public SubscribeCurrencyCommand(UserService userService) {
        super(SUBSCRIBE_CURRENCY.getCommandIdentifier(), SUBSCRIBE_CURRENCY.getCommandDescription());
        this.userService = userService;
    }

    @Override
    public void execute(AbsSender sender, User user) {
        String message = "";
        if (userService.checkIfUserExists(user.prepareUserName())) {
            message = """
                To subscribe currency just enter the currency symbol and the maximum percentage change
                For example: "APXUSDT 0.5"
                
                You'll be subscribed to APXUSDT currency and you'll be notified after 0.5% change
                """;
        } else {
            message = """
                    To subscribe currency you need to be authorized.
                    Try to execute `/start`...
                    """;
        }
        sendAnswer(sender, user, message);
    }
}
