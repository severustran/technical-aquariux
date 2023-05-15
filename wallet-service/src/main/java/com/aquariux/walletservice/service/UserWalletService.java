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

@Service
@RequiredArgsConstructor
@Slf4j
public class UserWalletService {
    private final UserWalletRepository userWalletRepository;
    private final CoinPairRepository coinPairRepository;
    private final UserClient userClient;
    @Transactional(readOnly = true)
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
        List<UserWalletEntity> wallets = userWalletRepository.findAllByUserId(requestDto.userId());
        if (wallets.isEmpty()) {
            throw new EntityNotFoundException("Not found wallet coin pair %s of userId %s" + requestDto.userId());
        }

        for (UserWalletEntity userWallet : wallets) {
            if (userWallet.getCoinPair().getCoinPair().equals(coinPairEntity.getCoinPair())) {
                BigDecimal balance = userWallet.getBalance();
                BigDecimal blockedBalance = userWallet.getBlockedBalance();
                BigDecimal orderBalance = requestDto.price().multiply(requestDto.quantity());

                if (userWallet.getStatus().equals(WalletStatusEnum.BLOCKED)) {
                    log.info("Wallet {} is blocked", userWallet.getCoinPair().getCoinPair());
                    return false;
                }

                if (orderBalance.compareTo(balance) > 0) {
                    log.info("Insufficient Wallet {} Balance", userWallet.getCoinPair().getCoinPair());
                    return false;
                }
                userWallet.setBlockedBalance(blockedBalance.add(orderBalance));
                userWalletRepository.save(userWallet);
                userWalletRepository.updateWalletBalance(requestDto.userId(), balance.subtract(orderBalance));
                break;
            }
        }
        return true;
    }

    @Transactional
    public boolean verifyAndCheckAvailableWalletBalance(TradeRequestDto requestDto) {
        CoinPairEntity coinPairEntity = coinPairRepository.findByCoinPair(CoinPairEnum.fromString(requestDto.coinPair()))
                .orElseThrow(() -> new EntityNotFoundException("Not Found Coin Pair %s" + requestDto.coinPair()));
        List<UserWalletEntity> wallets = userWalletRepository.findAllByUserId(requestDto.userId());

        if (wallets.isEmpty()) {
            throw new EntityNotFoundException("Not found wallet coin pair %s of userId %s" + requestDto.userId());
        }
        for (UserWalletEntity userWallet : wallets) {
            if (userWallet.getCoinPair().getCoinPair().equals(coinPairEntity.getCoinPair())) {
                BigDecimal balance = userWallet.getBalance();
                BigDecimal availableBalance = userWallet.getAvailableBalance();
                BigDecimal blockedBalance = userWallet.getBlockedBalance();
                BigDecimal orderBalance = requestDto.price().multiply(requestDto.quantity());

                if (userWallet.getStatus().equals(WalletStatusEnum.BLOCKED)) {
                    log.info("Wallet {} is blocked", userWallet.getCoinPair().getCoinPair());
                    return false;
                }

                if (availableBalance.compareTo(BigDecimal.ZERO) <= 0) {
                    log.info("Insufficient Wallet {} Balance", userWallet.getCoinPair().getCoinPair());
                    return false;
                }

                if (availableBalance.compareTo(orderBalance) >= 0 && orderBalance.compareTo(BigDecimal.ZERO) > 0 ) {
                    userWallet.setAvailableBalance(availableBalance.subtract(orderBalance));
                    userWallet.setBlockedBalance(blockedBalance.add(orderBalance));
                    userWalletRepository.save(userWallet);
                }

                userWalletRepository.updateWalletBalance(requestDto.userId(), balance.subtract(orderBalance));
                break;
            }
        }
        return true;
    }
}
