package com.example.util;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
@Slf4j
public class Constants {

    @Value("${telegram.bot.token}")
    private String botToken;

    @Value("${telegram.bot.max_users}")
    private Long maxUsers;

    @Value("${telegram.bot.username}")
    private String botUsername;

    @Value("${telegram.bot.update_delay_in_ms}")
    private String updateDelayInMs;

    public static final String COMMAND_KEY = "command";
    public static final String COMMAND_PREFIX = "/";

    public static final String CURRENCY_PRICE_MESSAGE_KEY = "currency_price_message";
    public static final String CURRENCY_SUBSCRIBE_MESSAGE_KEY = "currency_subscribe_message";
}
