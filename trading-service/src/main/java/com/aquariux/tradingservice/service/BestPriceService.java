package com.aquariux.tradingservice.service;

import com.aquariux.tradingservice.client.BinanceClient;
import com.aquariux.tradingservice.client.HoubiClient;
import com.aquariux.tradingservice.client.UserWalletClient;
import com.aquariux.tradingservice.dto.BestPriceDto;
import com.aquariux.tradingservice.dto.BinanceTickerDto;
import com.aquariux.tradingservice.dto.HoubiResponse;
import com.aquariux.tradingservice.entity.BestPriceEntity;
import com.aquariux.tradingservice.entity.LatestBestPriceEntity;
import com.aquariux.tradingservice.enums.CoinPairEnum;
import com.aquariux.tradingservice.enums.ExchangeEnum;
import com.aquariux.tradingservice.repository.BestPriceRepository;
import com.aquariux.tradingservice.repository.LatestBestPriceRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BestPriceService {
    private final BestPriceRepository bestPriceRepository;
    private final LatestBestPriceRepository latestBestPriceRepository;
    private final BinanceClient binanceClient;
    private final UserWalletClient userWalletClient;
    private final HoubiClient houbiClient;
    private final ObjectMapper objectMapper;

    @Scheduled(fixedRateString = "${spring.application.api.scheduler.intervals}")
    @Async(value = "bestPriceExecutor")
    @Transactional
    public void retrieveBestPricing() {
        List<BestPriceEntity> entities = new ArrayList<>();
        List<BestPriceDto> binancePrices = this.getBinancePrice();
        List<BestPriceDto> houbiPrices = this.getHoubiPrice();
        if (binancePrices.isEmpty() && houbiPrices.isEmpty()) {
            log.info("No pricing found!");
        } else if (binancePrices.isEmpty()) {
            entities = this.mapToBestPricingEntity(houbiPrices);
        } else if (houbiPrices.isEmpty()) {
            entities = this.mapToBestPricingEntity(binancePrices);
        } else {
            List<BestPriceDto> bestPrices = new ArrayList<>();
           for (BestPriceDto binancePrice : binancePrices) {
               for (BestPriceDto houbiPrice : houbiPrices) {
                   if (binancePrice.coinPair().toString().equals(houbiPrice.coinPair().toString())) {
                       BigDecimal bestBidPrice = (binancePrice.bidPrice().compareTo(houbiPrice.bidPrice()) > 0)
                               ? binancePrice.bidPrice() : houbiPrice.bidPrice();
                       BigDecimal bestAskPrice = (binancePrice.askPrice().compareTo(houbiPrice.askPrice()) < 0)
                               ? binancePrice.askPrice() : houbiPrice.askPrice();
                       bestPrices.add(BestPriceDto.builder()
                                       .coinPair(binancePrice.coinPair())
                                       .askPrice(bestAskPrice)
                                       .bidPrice(bestBidPrice)
                               .build());
                   }
               }
           }
           entities = this.mapToBestPricingEntity(bestPrices);
        }
        bestPriceRepository.saveAll(entities);
        this.checkAndSaveLatestBestPriceData(entities);
    }

    private List<BestPriceEntity> mapToBestPricingEntity(List<BestPriceDto> dtos) {
        return objectMapper.convertValue(dtos, new TypeReference<>() {});
    }

    private List<BestPriceDto> getBinancePrice() {
        List<BestPriceDto> priceDtos = new ArrayList<>();
        ResponseEntity<Object> responseData = binanceClient.getTickers();
        if (responseData.getStatusCode().is2xxSuccessful()) {
            List<BinanceTickerDto> binanceTickers = objectMapper.convertValue(responseData.getBody(), new TypeReference<>() {});
            if (!binanceTickers.isEmpty()) {
                priceDtos = binanceTickers.stream().filter(binanceTickerDto ->
                                CoinPairEnum.ETHUSDT.toString().equalsIgnoreCase(binanceTickerDto.symbol().toUpperCase())
                                        || CoinPairEnum.BTCUSDT.toString().equalsIgnoreCase(binanceTickerDto.symbol()))
                        .map(binanceTickerDto -> BestPriceDto.builder()
                                .coinPair(CoinPairEnum.valueOf(binanceTickerDto.symbol().toUpperCase()))
                                .askPrice(binanceTickerDto.askPrice())
                                .bidPrice(binanceTickerDto.bidPrice())
                                .exchange(ExchangeEnum.BINANCE)
                                .build()).collect(Collectors.toList());
            }
        } else {
            log.error("Error when retrieving BINANCE data!, {}", responseData.getBody());
        }
        return priceDtos;
    }

    private List<BestPriceDto> getHoubiPrice() {
        List<BestPriceDto> priceDtos = new ArrayList<>();
        ResponseEntity<Object> responseData = houbiClient.getTickers();
        if (responseData.getStatusCode().is2xxSuccessful()) {
            HoubiResponse houbiData = objectMapper.convertValue(responseData.getBody(), new TypeReference<>() {});
            if (!houbiData.data().isEmpty()) {
                priceDtos = houbiData.data().stream().filter(binanceTickerDto ->
                                CoinPairEnum.ETHUSDT.toString().equalsIgnoreCase(binanceTickerDto.symbol().toUpperCase())
                                        || CoinPairEnum.BTCUSDT.toString().equalsIgnoreCase(binanceTickerDto.symbol()))
                        .map(binanceTickerDto -> BestPriceDto.builder()
                                .coinPair(CoinPairEnum.valueOf(binanceTickerDto.symbol().toUpperCase()))
                                .askPrice(binanceTickerDto.ask())
                                .bidPrice(binanceTickerDto.bid())
                                .exchange(ExchangeEnum.HUOBI)
                                .build()).collect(Collectors.toList());
            }

        } else {
            log.error("Error when retrieving HUOBI data!, {}", responseData.getBody());
        }
        return priceDtos;
    }

    public void checkAndSaveLatestBestPriceData(List<BestPriceEntity> priceEntities) {
        if (!priceEntities.isEmpty()) {
            for (BestPriceEntity entity : priceEntities) {
                Optional<LatestBestPriceEntity> latestBestPriceEntityOptional = latestBestPriceRepository.findByCoinPair(entity.getCoinPair());
                if (latestBestPriceEntityOptional.isPresent()) {
                    latestBestPriceRepository.updateBestPriceByExchangeAndCoinPair(entity.getBidPrice(),
                            entity.getAskPrice(), entity.getCoinPair());
                } else {
                    latestBestPriceRepository.save(LatestBestPriceEntity.builder()
                            .coinPair(entity.getCoinPair())
                            .bidPrice(entity.getBidPrice())
                            .askPrice(entity.getAskPrice())
                            .build());
                }
            }
        }
    }
}
