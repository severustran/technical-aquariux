package com.aquariux.tradingservice.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record HoubiResponse(
        String ts, String status, List<HoubiTickerDto> data
) {}
