package com.example.service;

import com.example.util.Constants;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;

@Service
@Setter
@RequiredArgsConstructor
public class UserService {

    private final Constants constants;
    private final AtomicLong userCounter = new AtomicLong();

    @PostConstruct
    private void init() {
        userCounter.lazySet(constants.getMaxUsers());
    }

    public void registerUser() {}
}
