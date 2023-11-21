package com.example.service;

import com.example.command.Command;
import com.example.exception.UnhandledCommandException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class MessageService {

    private final HashMap<String, Command> commands;

    public void handleMessage() {

    }

    private Command getCommandByIdentifier(String identifier) throws UnhandledCommandException {
        Command command = commands.get(identifier);
        if (Objects.isNull(command)) {
            throw new UnhandledCommandException("Command " + identifier + " is not supported");
        }
        return command;
    }
}
