package com.aquariux.walletservice.dto;

import com.aquariux.walletservice.enums.CoinPairEnum;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record CoinPairDto(
        Long id,
        CoinPairEnum coinPair,
        boolean isActive,
        BigDecimal rateInUSDT
) {}
