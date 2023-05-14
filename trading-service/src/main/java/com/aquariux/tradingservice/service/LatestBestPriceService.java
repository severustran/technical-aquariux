package com.aquariux.tradingservice.service;

import com.aquariux.tradingservice.dto.LatestBestPriceDto;
import com.aquariux.tradingservice.entity.LatestBestPriceEntity;
import com.aquariux.tradingservice.enums.CoinPairEnum;
import com.aquariux.tradingservice.repository.LatestBestPriceRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class LatestBestPriceService {
    private static final String NOT_FOUND_ENTITY_BY_COIN_PAIR = "Not found latest best price entity by coin pair %s";
    private final LatestBestPriceRepository latestBestPriceRepository;
    private final ObjectMapper objectMapper;
    public LatestBestPriceDto getLatestBestPriceByCoinPair(String coinPair) {
        Optional<LatestBestPriceEntity> optionalEntity = latestBestPriceRepository.findByCoinPair(CoinPairEnum.fromString(coinPair.toUpperCase()));
        if (optionalEntity.isPresent()) {
            return objectMapper.convertValue(optionalEntity.get(), LatestBestPriceDto.class);
        } else {
            log.error("Not found entity by coin pair {}", coinPair);
            throw new EntityNotFoundException(String.format(NOT_FOUND_ENTITY_BY_COIN_PAIR, coinPair.toUpperCase()));
        }
    }

}
