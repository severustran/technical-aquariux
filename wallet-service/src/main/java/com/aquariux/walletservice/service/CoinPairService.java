package com.aquariux.walletservice.service;

import com.aquariux.walletservice.enums.CoinPairEnum;
import com.aquariux.walletservice.repository.CoinPairRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CoinPairService {
    private final CoinPairRepository coinPairRepository;
    @Transactional
    public void updateRateInUSDT(BigDecimal rateInUSDT, CoinPairEnum coinPairEnum) {
        coinPairRepository.updateRateInUSDT(rateInUSDT, coinPairEnum);
    }
}
