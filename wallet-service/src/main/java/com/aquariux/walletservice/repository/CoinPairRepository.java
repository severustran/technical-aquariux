package com.aquariux.walletservice.repository;

import com.aquariux.walletservice.entity.CoinPairEntity;
import com.aquariux.walletservice.enums.CoinPairEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface CoinPairRepository extends JpaRepository<CoinPairEntity, Long> {
    Optional<CoinPairEntity> findByCoinPair(CoinPairEnum coinPair);
    @Modifying
    @Query(value = "update CoinPairEntity c set c.rateInUSDT = :rateInUSDT where c.coinPair = :coinPair")
    int updateRateInUSDT(BigDecimal rateInUSDT, CoinPairEnum coinPair);
}
