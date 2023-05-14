package com.aquariux.walletservice.controller.advice;

import com.aquariux.walletservice.dto.response.ErrorResponseDto;
import feign.FeignException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class WalletControllerAdvice {
    @ExceptionHandler(FeignException.class)
    public ResponseEntity<ErrorResponseDto> feignException(FeignException exception) {
        return badRequest(exception);
    }

    private ResponseEntity<ErrorResponseDto> badRequest(Throwable throwable) {
        return ResponseEntity.badRequest().body(ErrorResponseDto.builder()
                .message(throwable.getMessage())
                .time(LocalDateTime.now())
                .build());
    }
}
