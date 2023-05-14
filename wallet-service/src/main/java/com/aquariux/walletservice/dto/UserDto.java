package com.aquariux.walletservice.dto;

import com.aquariux.walletservice.enums.UserStatusEnum;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record UserDto(
        Long id,
        String email,
        UserStatusEnum userStatus,
        LocalDateTime createdAt)
{}
