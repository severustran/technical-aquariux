package com.aquariux.userservice.dto;

import com.aquariux.userservice.enums.UserStatusEnum;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record UserDto(
        Long id,
        String email,
        UserStatusEnum userStatus,
        LocalDateTime createdAt)
{}
