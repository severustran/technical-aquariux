package com.aquariux.walletservice.service;

import com.aquariux.walletservice.client.UserClient;
import com.aquariux.walletservice.dto.UserDto;
import com.aquariux.walletservice.dto.response.UserWalletResponseDto;
import com.aquariux.walletservice.entity.UserWalletEntity;
import com.aquariux.walletservice.repository.UserWalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserWalletService {
    private final UserWalletRepository userWalletRepository;
    private final UserClient userClient;
    public List<UserWalletResponseDto> getUserWalletsBalance(Long userId) {
        List<UserWalletResponseDto> userWallets = new ArrayList<>();
        ResponseEntity<UserDto> user = userClient.getUser(userId);
        if (user.getStatusCode().is2xxSuccessful() && Objects.nonNull(user.getBody())) {
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
        }
        return userWallets;
    }
}
