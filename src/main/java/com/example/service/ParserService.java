package com.example.service;

import com.example.entity.Currency;
import com.example.exception.ParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ParserService {
    private final ObjectMapper objectMapper;

    public ParserService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public List<Currency> parseCryptoList(ResponseEntity<String> response) {
        if (response.getStatusCode().is2xxSuccessful()) {
            try {
                String responseBody = response.getBody();
                return parseCurrencyList(responseBody);
            } catch (IOException e) {
                throw new ParseException("Failed to parse crypto list response");
            }
        } else {
            throw new ParseException("Failed to fetch crypto list");
        }
    }

    public Currency parseCryptoItem(ResponseEntity<String> response) {
        if (response.getStatusCode().is2xxSuccessful()) {
            try {
                String responseBody = response.getBody();
                return parseCurrency(responseBody);
            } catch (IOException e) {
                throw new ParseException("Failed to parse crypto item response");
            }
        } else {
            throw new ParseException("Failed to fetch crypto item");
        }
    }

    private List<Currency> parseCurrencyList(String responseBody) throws IOException {
        return objectMapper.readValue(responseBody, new TypeReference<>() {});
    }

    private Currency parseCurrency(String responseBody) throws IOException {
        return objectMapper.readValue(responseBody, Currency.class);
    }
}
