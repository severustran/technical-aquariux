package com.aquariux.tradingservice.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record BinanceTickerDto(
        String symbol,
        BigDecimal bidPrice,
        BigDecimal bidQty,
        BigDecimal askPrice,
        BigDecimal askQty
) {}
