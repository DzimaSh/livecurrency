package com.example.command;

import com.example.entity.User;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.bots.AbsSender;

import static com.example.command.CommandDetails.CHECK_CURRENCY;

@Component
public class CheckCurrencyCommand extends Command {

    public CheckCurrencyCommand() {
        super(CHECK_CURRENCY.getCommandIdentifier(), CHECK_CURRENCY.getCommandDescription());
    }

    @Override
    public void execute(AbsSender sender, User user) {
        sendAnswer(sender, user,
                """
                To check currency status just enter the currency symbol
                For example: "APXUSDT"
                """);
    }
}
