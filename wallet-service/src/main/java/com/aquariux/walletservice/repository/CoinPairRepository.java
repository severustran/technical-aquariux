package com.aquariux.walletservice.repository;

import com.aquariux.walletservice.entity.CoinPairEntity;
import com.aquariux.walletservice.enums.CoinPairEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CoinPairRepository extends JpaRepository<CoinPairEntity, Long> {
    Optional<CoinPairEntity> findByCoinPair(CoinPairEnum coinPair);
}
