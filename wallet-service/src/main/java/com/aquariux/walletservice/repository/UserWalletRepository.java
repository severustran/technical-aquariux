package com.aquariux.walletservice.repository;

import com.aquariux.walletservice.entity.UserWalletEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserWalletRepository extends JpaRepository<UserWalletEntity, Long> {
    List<UserWalletEntity> findAllByUserId(Long userId);
}
