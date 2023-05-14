package com.aquariux.tradingservice.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ErrorResponseDto(
        String message,
        LocalDateTime time
) {}
