package com.aquariux.tradingservice.dto.request;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record OrderRequestDto(
   BigDecimal quantity,
   String coinPair
) {}
