package com.livecurrency.service;

import com.livecurrency.entity.Currency;
import com.livecurrency.entity.Request;
import com.livecurrency.entity.User;
import com.livecurrency.repository.RequestRepository;
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
    private final UserService userService;

    @Transactional
    public Request subscribe(User user, String currencySymbol, Double percentage) {
        User liveUser = userService.getUserByUsername(user.getUserName());
        Currency currency = currencyService.getBySymbol(currencySymbol);

        Request request = new Request();
        request.setUser(liveUser);
        request.setCurrency(currency);
        request.setPercents(percentage);

        return repository.save(request);
    }


    public Double calculateSignedPercentageChange(Request request) {
        if (request.getInitialPrice() == null ||
                request.getCurrency() == null ||
                request.getCurrency().getPrice() == null) {
            throw new IllegalStateException("Initial price or current price is null");
        }

        return ((request.getCurrency().getPrice() - request.getInitialPrice()) / request.getInitialPrice()) * 100;
    }
}
