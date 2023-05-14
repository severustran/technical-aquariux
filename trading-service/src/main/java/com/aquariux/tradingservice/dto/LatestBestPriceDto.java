package com.aquariux.tradingservice.dto;

import com.aquariux.tradingservice.enums.CoinPairEnum;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record LatestBestPriceDto(
        CoinPairEnum coinPair,
        BigDecimal bidPrice,
        BigDecimal askPrice)
{}
