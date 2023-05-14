package com.aquariux.tradingservice.entity;

import com.aquariux.tradingservice.enums.CoinPairEnum;
import com.aquariux.tradingservice.enums.ExchangeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "latest_best_price")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LatestBestPriceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(name = "coin_pair")
    private CoinPairEnum coinPair;
    private BigDecimal bidPrice;
    private BigDecimal askPrice;
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
