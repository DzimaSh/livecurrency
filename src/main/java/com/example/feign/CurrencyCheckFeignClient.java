package com.example.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "currencyCheckFeign", url = "${crypto.url}")
public interface CurrencyCheckFeignClient {
    @GetMapping
    ResponseEntity<String> getCryptoList();

    @GetMapping
    ResponseEntity<String> getCryptoItem(@RequestParam("symbol") String symbol);
}
