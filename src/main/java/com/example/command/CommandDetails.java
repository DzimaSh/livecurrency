package com.example.command;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum CommandDetails {
    SETTINGS("/settings", "Settings"),
    START("/start", "Start"),
    CHECK_CURRENCY("/check_currency", "Check the price of given currency");

    private final String commandIdentifier;
    private final String commandDescription;
}
