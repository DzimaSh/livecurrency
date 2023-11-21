package com.example.command;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Locale;

@RequiredArgsConstructor
@Getter
public enum CommandDetails {
    SETTINGS("/settings", "Settings"),
    START("/start", "Start");

    private final String commandIdentifier;
    private final String commandDescription;
}
