package com.example.entity;

import lombok.Data;

import java.util.List;

@Data
public class User {
    private Long id;
    private String username;
    private List<Request> requests;
    private Long chatId;
    private Long permissions;
}
