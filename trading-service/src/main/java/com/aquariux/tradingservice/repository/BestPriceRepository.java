package com.aquariux.tradingservice.repository;

import com.aquariux.tradingservice.entity.BestPriceEntity;
import com.aquariux.tradingservice.enums.ExchangeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BestPriceRepository extends JpaRepository<BestPriceEntity, Long> {
    List<BestPriceEntity> findByExchange(ExchangeEnum exchange);
}
