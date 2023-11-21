package com.example.util;

import jakarta.annotation.PostConstruct;
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

    @PostConstruct
    private void postConstruct() {
        log.info("Constants initialized" + this.getBotToken());
    }
}
