package com.example.command;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Locale;

@RequiredArgsConstructor
@Getter
public enum CommandEnum {
    SETTINGS("/settings", "Settings"),
    START("/start", "Start");

    private final String commandIdentifier;
    private final String commandDescription;

    public static CommandEnum getByIdentifier(String identifier) {
        return CommandEnum.valueOf(identifier
                .toUpperCase(Locale.ROOT)
                .replace("/", ""));
    }
}
