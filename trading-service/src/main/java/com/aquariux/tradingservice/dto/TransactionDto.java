package com.aquariux.tradingservice.dto;

import com.aquariux.tradingservice.enums.CoinPairEnum;
import com.aquariux.tradingservice.enums.TradingTypeEnum;
import com.aquariux.tradingservice.enums.TransactionStatusEnum;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public record TransactionDto(
        Long userId,
        TradingTypeEnum tradingType,
        CoinPairEnum coinPair,
        BigDecimal price,
        BigDecimal quantity,
        TransactionStatusEnum transactionStatus,
        LocalDateTime createdAt
) {}
