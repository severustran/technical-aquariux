package com.aquariux.tradingservice.controller;

import com.aquariux.tradingservice.dto.request.TradeRequestDto;
import com.aquariux.tradingservice.dto.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/trade")
@RequiredArgsConstructor
@Slf4j
public class TradeController {

    @PostMapping("/buy")
    public ResponseEntity<ResponseDto> buy(@RequestHeader Long userId, @RequestBody TradeRequestDto requestDto) {
        return null;
    }

    @PostMapping("/sell")
    public ResponseEntity<ResponseDto> sell(@RequestHeader Long userId, @RequestBody TradeRequestDto requestDto) {
        return null;
    }
}
