package com.aquariux.tradingservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "binance-client", url = "${spring.application.api.binance}")
public interface BinanceClient {
    @GetMapping
    ResponseEntity<Object> getTickers();
}
