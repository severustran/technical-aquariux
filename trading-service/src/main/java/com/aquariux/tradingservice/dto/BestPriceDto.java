package com.aquariux.tradingservice.dto;

import com.aquariux.tradingservice.enums.CoinPairEnum;
import com.aquariux.tradingservice.enums.ExchangeEnum;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record BestPriceDto(
        CoinPairEnum coinPair,
        BigDecimal bidPrice,
        BigDecimal askPrice,
        ExchangeEnum exchange
) {}
