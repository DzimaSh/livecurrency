package com.example.command;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum CommandEnum {
    SETTINGS("/settings");

    private final String command;
}
