package com.aquariux.walletservice.controller;

import com.aquariux.walletservice.dto.request.TradeRequestDto;
import com.aquariux.walletservice.service.UserWalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
    private final UserWalletService userWalletService;
    @PostMapping("/buy-verify")
    public ResponseEntity<Boolean> verifyAndBlockBalance(@RequestBody TradeRequestDto requestDto) {
        return ResponseEntity.ok(userWalletService.verifyAndBlockBalance(requestDto));
    }

    @PostMapping("/sell-verify")
    public ResponseEntity<Boolean> verifyAndCheckAvailableWalletBalance(@RequestBody TradeRequestDto requestDto) {
        return ResponseEntity.ok(userWalletService.verifyAndCheckAvailableWalletBalance(requestDto));
    }
}
