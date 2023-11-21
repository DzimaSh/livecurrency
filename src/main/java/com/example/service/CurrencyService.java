package com.example.service;

import com.example.feign.CurrencyCheckFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrencyService {
    private final CurrencyCheckFeignClient currencyCheckClient;
    // private final CurrencyRepository repository;

    public boolean checkPrice(String currName) {
        return false;
    }
}
