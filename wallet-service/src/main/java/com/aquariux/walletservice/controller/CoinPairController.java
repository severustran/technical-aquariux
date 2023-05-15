package com.aquariux.walletservice.controller;

import com.aquariux.walletservice.enums.CoinPairEnum;
import com.aquariux.walletservice.service.CoinPairService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/coinPair")
@RequiredArgsConstructor
public class CoinPairController {
    private final CoinPairService coinPairService;
    @PostMapping
    public ResponseEntity<String> updateRateInUSDT(@RequestParam BigDecimal rateInUSDT, @RequestParam CoinPairEnum coinPairEnum) {
        coinPairService.updateRateInUSDT(rateInUSDT, coinPairEnum);
        return ResponseEntity.ok("OK");
    }
}
