package com.example.bot;

import com.example.util.Constants;
import org.springframework.stereotype.Component;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.objects.Ability;

@Component
public class LiveCurrencyBot extends AbilityBot {

    private final Constants constants;

    public LiveCurrencyBot(Constants constants) {
        super(constants.getBotToken(), constants.getBotUsername());
        this.constants = constants;
    }

    public Ability startBot() {
        return Ability.builder()
                .build();
    }

    @Override
    public long creatorId() {
        return 1L;
    }
}
