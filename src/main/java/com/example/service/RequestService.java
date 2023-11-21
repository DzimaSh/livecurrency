package com.example.service;

import com.example.entity.Request;
import com.example.entity.User;
import com.example.repository.RequestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RequestService {
    private final RequestRepository repository;
    private final CurrencyService currencyService;

    @Transactional
    public Request subscribe(User user, String currencySymbol, Double percentage) {
        return new Request();
    }
}
