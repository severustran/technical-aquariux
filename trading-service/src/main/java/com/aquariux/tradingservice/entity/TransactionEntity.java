package com.aquariux.tradingservice.entity;

import com.aquariux.tradingservice.enums.CoinPairEnum;
import com.aquariux.tradingservice.enums.TradingTypeEnum;
import com.aquariux.tradingservice.enums.TransactionStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

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
@Table(name = "transaction")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class TransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_id")
    private Long userId;
    @Enumerated(EnumType.STRING)
    @Column(name = "trading_type")
    private TradingTypeEnum tradingType;
    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_status")
    private TransactionStatusEnum transactionStatus;
    @Enumerated(EnumType.STRING)
    @Column(name = "coin_pair")
    private CoinPairEnum coinPair;
    @Column(scale = 8, precision = 20)
    private BigDecimal price;
    @Column(scale = 8, precision = 20)
    private BigDecimal quantity;
    @CreationTimestamp
    private LocalDateTime createdAt;
}
