package com.aquariux.walletservice.dto.request;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record TradeRequestDto(
        Long userId,
        String coinPair,
        BigDecimal quantity,
        BigDecimal price
) {
}
