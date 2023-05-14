package com.aquariux.tradingservice.service;

import com.aquariux.tradingservice.client.UserWalletClient;
import com.aquariux.tradingservice.dto.LatestBestPriceDto;
import com.aquariux.tradingservice.dto.request.OrderRequestDto;
import com.aquariux.tradingservice.dto.request.TradeRequestDto;
import com.aquariux.tradingservice.dto.response.ResponseDto;
import com.aquariux.tradingservice.entity.TransactionEntity;
import com.aquariux.tradingservice.enums.CoinPairEnum;
import com.aquariux.tradingservice.enums.TradingTypeEnum;
import com.aquariux.tradingservice.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final UserWalletClient userWalletClient;
    private final LatestBestPriceService latestBestPriceService;
    private final TransactionRepository transactionRepository;

    @Transactional
    public ResponseDto buy(Long userId, OrderRequestDto requestDto) {
        String message = "Order Failed";
        String status = HttpStatus.BAD_REQUEST.toString();
        LatestBestPriceDto latestBestPrice = latestBestPriceService.getLatestBestPriceByCoinPair(requestDto.coinPair());
        TradeRequestDto tradeRequestDto = TradeRequestDto.builder()
                .coinPair(requestDto.coinPair())
                .userId(userId)
                .quantity(requestDto.quantity())
                .price(latestBestPrice.askPrice())
                .build();
        ResponseEntity<Boolean> response = userWalletClient.verifyAndBlockBalance(tradeRequestDto);
        if (response.getStatusCode().is2xxSuccessful() && Objects.nonNull(response.getBody())) {
            if (Boolean.TRUE.equals(response.getBody())) {
                message = "Order Successfully";
                status = HttpStatus.OK.toString();
                TransactionEntity transaction = TransactionEntity.builder()
                        .tradingType(TradingTypeEnum.BUY)
                        .quantity(requestDto.quantity())
                        .coinPair(CoinPairEnum.fromString(requestDto.coinPair()))
                        .price(latestBestPrice.askPrice())
                        .userId(userId)
                        .build();
                transactionRepository.save(transaction);
            }
        }
        return ResponseDto.builder()
                .message(message)
                .status(status)
                .build();
    }
}
