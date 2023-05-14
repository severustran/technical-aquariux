package com.aquariux.walletservice.client;

import com.aquariux.walletservice.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("${spring.application.user-service}")
public interface UserClient {
    @GetMapping("/user")
    ResponseEntity<UserDto> getUser(@RequestParam("userId") Long userId);
}
