package com.aquariux.tradingservice.dto.request;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record TradeRequestDto(
        Long userId,
        String coin,
        BigDecimal quantity
) {
}
