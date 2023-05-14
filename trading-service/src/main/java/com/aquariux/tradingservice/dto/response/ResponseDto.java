package com.aquariux.tradingservice.dto.response;

import lombok.Builder;

@Builder
public record ResponseDto(
        String status,
        String message
) {
}
