package com.livecurrency.command;

import com.livecurrency.entity.User;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.bots.AbsSender;

import static com.livecurrency.command.CommandDetails.CHECK_CURRENCY;

@Component
public class CheckCurrencyCommand extends Command {

    private static final String RESPONSE =
                """
                To check currency status just enter the currency symbol
                For example: "APXUSDT"
                """;

    public CheckCurrencyCommand() {
        super(CHECK_CURRENCY.getCommandIdentifier(), CHECK_CURRENCY.getCommandDescription());
    }

    @Override
    public void execute(AbsSender sender, User user) {
        sendAnswer(sender, user, RESPONSE);
    }
}
