package com.aquariux.walletservice.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ErrorResponseDto(
        String message,
        LocalDateTime time
) {}

