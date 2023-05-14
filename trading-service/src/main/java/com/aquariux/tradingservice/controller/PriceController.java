package com.aquariux.tradingservice.controller;

import com.aquariux.tradingservice.dto.LatestBestPriceDto;
import com.aquariux.tradingservice.service.LatestBestPriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/price")
@RequiredArgsConstructor
public class PriceController {
    private final LatestBestPriceService latestBestPriceService;
    @GetMapping("/coinPair")
    public ResponseEntity<LatestBestPriceDto> getLatestBestPriceByCoinPair(@RequestParam String coinPair) {
        LatestBestPriceDto response = latestBestPriceService.getLatestBestPriceByCoinPair(coinPair);
        return ResponseEntity.ok(response);
    }
}
