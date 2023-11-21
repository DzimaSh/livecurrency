package com.example.command;

import com.example.entity.User;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.bots.AbsSender;

import static com.example.command.CommandDetails.START;

@Component
public class StartCommand extends Command {
    public StartCommand() {
        super(START.getCommandIdentifier(), START.getCommandDescription());
    }

    @Override
    public void execute(AbsSender sender, User user) {

    }
}
