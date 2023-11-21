package com.example.service;

import com.example.entity.Currency;
import com.example.entity.Request;
import com.example.feign.CurrencyCheckFeignClient;
import com.example.repository.CurrencyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RefreshService {
    private final CurrencyCheckFeignClient currencyCheckClient;
    private final CurrencyRepository repository;
    private final ParserService parserService;
    private final MessageService messageService;

    @Transactional
    @Scheduled(fixedDelayString = "${telegram.bot.update_delay_in_ms}")
    public void refreshCurrenciesState() {
        int[] counter = { 0 };
        List<Currency> updatedCurrencyList = parserService
                .parseCryptoList(currencyCheckClient.getCryptoList());

        updatedCurrencyList.forEach(updatedCurrency -> {
            Optional<Currency> existingCurrency = repository
                    .findBySymbol(updatedCurrency.getSymbol());

            existingCurrency.ifPresentOrElse(
                    currency -> {
                        currency.setPrice(updatedCurrency.getPrice());
                        repository.save(currency);
                        counter[0]++;
                    },
                    () -> repository.save(updatedCurrency)
            );
        });

        repository.flush();

        log.info(String.format("Updated %d entities. Saved %d entities",
                counter[0], (updatedCurrencyList.size() - counter[0]))
        );

        notifyAllSubscribers();
    }

    private void notifyAllSubscribers() {
        List<Currency> currencies = repository.findAll();

        currencies.forEach(currency -> {
            List<Request> requests = currency.getRequests();
            requests.forEach(request -> {
                double percentageChange = request.calculateSignedPercentageChange();
                if (Math.abs(percentageChange) >= request.getPercents()) {
                    messageService.sendMessage(request.getUser(), String.format(
                            "The currency %s has changed by %.5f%%",
                            request.getCurrency().getSymbol(), percentageChange)
                    );
                }
            });
        });
    }
}
