package com.aquariux.walletservice.entity;

import com.aquariux.walletservice.enums.CoinPairEnum;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "coin_pair")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CoinPairEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(name = "coin_pair")
    private CoinPairEnum coinPair;
    @Column(name = "is_active")
    private boolean isActive;
    @Column(name = "rate_in_usdt", scale = 8, precision = 20)
    private BigDecimal rateInUSDT;
    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    @CreationTimestamp
    private LocalDateTime updatedAt;
    @OneToMany(mappedBy = "coinPair")
    private List<UserWalletEntity> userWalletEntities;
}
