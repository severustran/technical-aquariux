package com.aquariux.tradingservice.client;

import com.aquariux.tradingservice.dto.request.TradeRequestDto;
import com.aquariux.tradingservice.enums.CoinPairEnum;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@FeignClient("${spring.application.wallet-service}")
public interface UserWalletClient {
    @PostMapping("/order/buy-verify")
    ResponseEntity<Boolean> verifyAndBlockBalance(@RequestBody TradeRequestDto requestDto);
    @PostMapping("/order/sell-verify")
    ResponseEntity<Boolean> verifyAndCheckAvailableWalletBalance(@RequestBody TradeRequestDto requestDto);
    @PostMapping("/coinPair")
    ResponseEntity<String> updateRateInUSDT(@RequestParam BigDecimal rateInUSDT, @RequestParam CoinPairEnum coinPairEnum);
}
