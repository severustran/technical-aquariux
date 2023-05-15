package com.aquariux.tradingservice.service;

import com.aquariux.tradingservice.client.UserWalletClient;
import com.aquariux.tradingservice.dto.LatestBestPriceDto;
import com.aquariux.tradingservice.dto.TransactionDto;
import com.aquariux.tradingservice.dto.request.OrderRequestDto;
import com.aquariux.tradingservice.dto.request.TradeRequestDto;
import com.aquariux.tradingservice.dto.response.ResponseDto;
import com.aquariux.tradingservice.entity.TransactionEntity;
import com.aquariux.tradingservice.enums.CoinPairEnum;
import com.aquariux.tradingservice.enums.TradingTypeEnum;
import com.aquariux.tradingservice.enums.TransactionStatusEnum;
import com.aquariux.tradingservice.repository.TransactionRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final UserWalletClient userWalletClient;
    private final LatestBestPriceService latestBestPriceService;
    private final TransactionRepository transactionRepository;
    private final ObjectMapper objectMapper;

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
                        .transactionStatus(TransactionStatusEnum.PENDING)
                        .build();
                transactionRepository.save(transaction);
            }
        }
        return ResponseDto.builder()
                .message(message)
                .status(status)
                .build();
    }

    @Transactional
    public ResponseDto sell(Long userId, OrderRequestDto requestDto) {
        String message = "Order Failed";
        String status = HttpStatus.BAD_REQUEST.toString();
        LatestBestPriceDto latestBestPrice = latestBestPriceService.getLatestBestPriceByCoinPair(requestDto.coinPair());
        TradeRequestDto tradeRequestDto = TradeRequestDto.builder()
                .coinPair(requestDto.coinPair())
                .userId(userId)
                .quantity(requestDto.quantity())
                .price(latestBestPrice.bidPrice())
                .build();
        ResponseEntity<Boolean> response = userWalletClient.verifyAndCheckAvailableWalletBalance(tradeRequestDto);
        if (response.getStatusCode().is2xxSuccessful() && Objects.nonNull(response.getBody())) {
            if (Boolean.TRUE.equals(response.getBody())) {
                message = "Order Successfully";
                status = HttpStatus.OK.toString();
                TransactionEntity transaction = TransactionEntity.builder()
                        .tradingType(TradingTypeEnum.SELL)
                        .quantity(requestDto.quantity())
                        .coinPair(CoinPairEnum.fromString(requestDto.coinPair()))
                        .price(latestBestPrice.askPrice())
                        .userId(userId)
                        .transactionStatus(TransactionStatusEnum.PENDING)
                        .build();
                transactionRepository.save(transaction);
            }
        }
        return ResponseDto.builder()
                .message(message)
                .status(status)
                .build();
    }

    public List<TransactionDto> getTradingHistory(Long userId) {
        List<TransactionDto> transactions = new ArrayList<>();
        List<TransactionEntity> entities = transactionRepository.findAllByUserId(userId);
        if (entities.size() > 0) {
            transactions = objectMapper.convertValue(entities, new TypeReference<>() {});
        }
        return transactions;
    }
}
