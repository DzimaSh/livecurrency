package com.example.service;

import com.example.entity.Currency;
import com.example.feign.CurrencyCheckFeignClient;
import com.example.repository.CurrencyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CurrencyService {
    private final CurrencyCheckFeignClient currencyCheckClient;
    private final CurrencyRepository repository;
    private final ParserService parserService;

    @Transactional
    public Currency checkPrice(String currName) {
        return new Currency();
    }
}
