package com.aquariux.userservice.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ErrorResponseDto(
        String message,
        LocalDateTime time
) {}
