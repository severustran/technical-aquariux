package com.aquariux.walletservice.entity;

import com.aquariux.walletservice.enums.WalletStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_wallet")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserWalletEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_id")
    private Long userId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coin_pair_id")
    private CoinPairEntity coinPair;
    @Column(scale = 8, precision = 20)
    private BigDecimal balance;
    @Column(name = "blocked_balance", scale = 8, precision = 20)
    private BigDecimal blockedBalance;
    @Column(name = "available_balance", scale = 8, precision = 20)
    private BigDecimal availableBalance;
    private String address;
    @Enumerated(EnumType.STRING)
    private WalletStatusEnum status;
    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
