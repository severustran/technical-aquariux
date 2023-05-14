package com.aquariux.walletservice.dto.response;

import com.aquariux.walletservice.enums.CoinPairEnum;
import com.aquariux.walletservice.enums.WalletStatusEnum;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public record UserWalletResponseDto(
        Long userId,
        CoinPairEnum coinPair,
        String address,
        BigDecimal balance,
        BigDecimal blockedBalance,
        BigDecimal availableBalance,
        WalletStatusEnum status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
