package com.aquariux.walletservice.service;

import com.aquariux.walletservice.dto.response.UserWalletResponseDto;
import com.aquariux.walletservice.entity.UserWalletEntity;
import com.aquariux.walletservice.repository.UserWalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserWalletService {
    private final UserWalletRepository userWalletRepository;
    public List<UserWalletResponseDto> getUserWalletsBalance(Long userId) {
        List<UserWalletResponseDto> userWallets = new ArrayList<>();
        List<UserWalletEntity> entityWallets = userWalletRepository.findAllByUserId(userId);
        if (!entityWallets.isEmpty()) {
            entityWallets.forEach(entity -> userWallets.add(UserWalletResponseDto.builder()
                            .userId(entity.getUserId())
                            .balance(entity.getBalance())
                            .blockedBalance(entity.getBlockedBalance())
                            .status(entity.getStatus())
                            .address(entity.getAddress())
                            .availableBalance(entity.getAvailableBalance())
                            .createdAt(entity.getCreatedAt())
                            .updatedAt(entity.getUpdatedAt())
                            .coinPair(entity.getCoinPair().getCoinPair())
                    .build()));
        }
        return userWallets;
    }
}
