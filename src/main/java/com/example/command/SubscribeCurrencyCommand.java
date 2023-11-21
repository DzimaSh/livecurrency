package com.example.command;

import com.example.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.bots.AbsSender;

import static com.example.command.CommandDetails.SUBSCRIBE_CURRENCY;

@Slf4j
@Component
public class SubscribeCurrencyCommand extends Command {

    public SubscribeCurrencyCommand() {
        super(SUBSCRIBE_CURRENCY.getCommandIdentifier(), SUBSCRIBE_CURRENCY.getCommandDescription());
    }

    @Override
    public void execute(AbsSender sender, User user) {
        sendAnswer(sender, user,
                """
                To subscribe currency just enter the currency symbol and the maximum percentage change
                For example: "APXUSDT 0.5%"
                """);
    }
}
