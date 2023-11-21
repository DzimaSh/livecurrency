package com.example.command;

import com.example.entity.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

import static com.example.command.CommandEnum.START;

public class StartCommand extends Command {
    public StartCommand() {
        super(START.getCommandIdentifier(), START.getCommandDescription());
    }

    @Override
    public void execute(AbsSender sender, User user) {

    }
}
