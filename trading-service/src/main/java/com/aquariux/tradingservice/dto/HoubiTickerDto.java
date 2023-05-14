package com.aquariux.tradingservice.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record HoubiTickerDto(
        String symbol, BigDecimal open, BigDecimal high, BigDecimal low, BigDecimal close,
        BigDecimal amount, BigDecimal vol, Long count, BigDecimal bid, BigDecimal bidSize,
        BigDecimal ask, BigDecimal askSize
) {}
