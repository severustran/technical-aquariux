package com.aquariux.walletservice.repository;

import com.aquariux.walletservice.entity.CoinPairEntity;
import com.aquariux.walletservice.entity.UserWalletEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserWalletRepository extends JpaRepository<UserWalletEntity, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<UserWalletEntity> findAllByUserId(Long userId);
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<UserWalletEntity> findByCoinPairAndUserId(CoinPairEntity coinPair, Long userId);
    @Modifying
    @Query(value = "UPDATE UserWalletEntity u SET u.balance = :balance WHERE u.userId = :userId")
    int updateWalletBalance(Long userId, BigDecimal balance);

}
