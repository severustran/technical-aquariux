package com.aquariux.tradingservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "houbi-client", url = "${spring.application.api.houbi}")
public interface HoubiClient {
    @GetMapping
    ResponseEntity<Object> getTickers();
}
