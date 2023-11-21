package com.example.service;

import com.example.entity.Currency;
import com.example.feign.CurrencyCheckFeignClient;
import com.example.repository.CurrencyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CurrencyService {
    private final CurrencyCheckFeignClient currencyCheckClient;
    private final CurrencyRepository repository;
    private final ParserService parserService;

    @Transactional
    public Currency updatePrice(String currSymbol) {
        Currency fetchedCurrency = parserService
                    .parseCryptoItem(currencyCheckClient.getCryptoItem(currSymbol));
        Optional<Currency> existingCurrency = repository.findBySymbol(currSymbol);

        if (existingCurrency.isPresent() && !Objects.isNull(fetchedCurrency)) {
            existingCurrency.get().setPrice(fetchedCurrency.getPrice());

            return repository.save(existingCurrency.get());
        } else {
            return repository.save(fetchedCurrency);
        }
    }
}
