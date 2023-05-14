package com.aquariux.walletservice.service;

import com.aquariux.walletservice.client.UserClient;
import com.aquariux.walletservice.dto.UserDto;
import com.aquariux.walletservice.dto.request.TradeRequestDto;
import com.aquariux.walletservice.dto.response.UserWalletResponseDto;
import com.aquariux.walletservice.entity.CoinPairEntity;
import com.aquariux.walletservice.entity.UserWalletEntity;
import com.aquariux.walletservice.enums.CoinPairEnum;
import com.aquariux.walletservice.enums.WalletStatusEnum;
import com.aquariux.walletservice.repository.CoinPairRepository;
import com.aquariux.walletservice.repository.UserWalletRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserWalletService {
    private final UserWalletRepository userWalletRepository;
    private final CoinPairRepository coinPairRepository;
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

    @Transactional
    public boolean verifyAndBlockBalance(TradeRequestDto requestDto) {
        CoinPairEntity coinPairEntity = coinPairRepository.findByCoinPair(CoinPairEnum.fromString(requestDto.coinPair()))
                .orElseThrow(() -> new EntityNotFoundException("Not Found Coin Pair %s" + requestDto.coinPair()));
        UserWalletEntity userWallet = userWalletRepository.findByCoinPairAndUserId(coinPairEntity, requestDto.userId())
                .orElseThrow(() -> new EntityNotFoundException("Not found wallet coin pair %s of userId %s " + requestDto.coinPair() + requestDto.userId()));

        if (userWallet.getStatus().equals(WalletStatusEnum.BLOCKED)) {
            return false;
        }

        BigDecimal balance = userWallet.getBalance();
        BigDecimal blockedBalance = userWallet.getBlockedBalance();
        BigDecimal orderBalance = requestDto.price().multiply(requestDto.quantity());

        if (orderBalance.compareTo(balance) > 0) {
            log.info("Insufficient Wallet Balance");
            return false;
        }

        userWallet.setBalance(balance.subtract(orderBalance));
        userWallet.setBlockedBalance(blockedBalance.add(orderBalance));
        userWalletRepository.save(userWallet);
        return true;
    }
}
