package com.example.feign;

import com.example.entity.Currency;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(url = "${crypto.url}")
public interface CurrencyCheckFeignClient {
    @GetMapping
    List<Currency> getCryptoList();

    @GetMapping
    Currency getCryptoItem(@RequestParam("symbol") String symbol);
}
