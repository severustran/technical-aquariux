package com.aquariux.tradingservice.client;

import com.aquariux.tradingservice.dto.request.TradeRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("${spring.application.wallet-service}")
public interface UserWalletClient {
    @PostMapping("/order")
    ResponseEntity<Boolean> verifyAndBlockBalance(@RequestBody TradeRequestDto requestDto);
}
