package com.aquariux.walletservice.controller;

import com.aquariux.walletservice.dto.response.UserWalletResponseDto;
import com.aquariux.walletservice.service.UserWalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/wallet")
@RequiredArgsConstructor
public class WalletController {
    private final UserWalletService userWalletService;

    @GetMapping
    public ResponseEntity<List<UserWalletResponseDto>> getUserWalletsBalance(@RequestHeader Long userId) {
        return ResponseEntity.ok(userWalletService.getUserWalletsBalance(userId));
    }

}
