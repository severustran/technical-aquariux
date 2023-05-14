package com.aquariux.tradingservice.repository;

import com.aquariux.tradingservice.entity.LatestBestPriceEntity;
import com.aquariux.tradingservice.enums.CoinPairEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface LatestBestPriceRepository extends JpaRepository<LatestBestPriceEntity, Long> {
    @Modifying
    @Query(value = "UPDATE LatestBestPriceEntity b SET b.askPrice = :askPrice, b.bidPrice = :bidPrice, b.updatedAt = NOW()" +
            " WHERE b.coinPair = :coinPair ")
    void updateBestPriceByExchangeAndCoinPair(BigDecimal bidPrice, BigDecimal askPrice, CoinPairEnum coinPair);
    Optional<LatestBestPriceEntity> findByCoinPair(CoinPairEnum coinPair);
}
