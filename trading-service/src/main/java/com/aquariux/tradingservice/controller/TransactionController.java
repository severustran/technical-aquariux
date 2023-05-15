package com.aquariux.tradingservice.controller;

import com.aquariux.tradingservice.dto.TransactionDto;
import com.aquariux.tradingservice.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/transaction")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;
    @GetMapping("/history")
    public ResponseEntity<List<TransactionDto>> getTransactionHistory(@RequestParam Long userId) {
        return ResponseEntity.ok(transactionService.getTradingHistory(userId));
    }
}
