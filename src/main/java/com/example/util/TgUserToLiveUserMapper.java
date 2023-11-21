package com.example.util;

import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;

public class TgUserToLiveUserMapper {
    public static com.example.entity.User mapToLiveUser(User tgUser, Chat chat) {
        com.example.entity.User liveUser = new com.example.entity.User();

        liveUser.setFirstName(tgUser.getFirstName());
        liveUser.setLastName(tgUser.getLastName());
        liveUser.setUserName(tgUser.getUserName());
        liveUser.setChatId(chat.getId());

        return liveUser;
    }
}
