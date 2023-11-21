package com.example.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
@RequiredArgsConstructor
public class Constants {

    @Value("${telegram.bot.token}")
    private final String botToken;

    @Value("${telegram.bot.max_users}")
    private final Long maxUsers;

    private final String botUsername;
}
