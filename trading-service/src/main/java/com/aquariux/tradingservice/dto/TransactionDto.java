package com.aquariux.tradingservice.dto;

import com.aquariux.tradingservice.enums.TradingTypeEnum;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record TransactionDto(
        Long userId,
        TradingTypeEnum tradingType,
        BigDecimal price,
        BigDecimal quantity
) {}
