package com.aquariux.walletservice.dto;

import com.aquariux.walletservice.enums.WalletStatusEnum;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public record UserWalletDto(
        Long userId,
        CoinPairDto coinPair,
        BigDecimal balance,
        BigDecimal blockedBalance,
        BigDecimal availableBalance,
        String address,
        WalletStatusEnum status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
