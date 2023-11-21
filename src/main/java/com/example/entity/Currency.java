package com.example.entity;

import lombok.Data;

@Data
public class Currency {
    private Long id;
    private String symbol;
    private Double price;
}
