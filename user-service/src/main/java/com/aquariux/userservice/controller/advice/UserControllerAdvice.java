package com.aquariux.userservice.controller.advice;

import com.aquariux.userservice.dto.response.ErrorResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;

@ControllerAdvice
public class UserControllerAdvice {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> entityNotFoundException(EntityNotFoundException exception) {
        return badRequest(exception);
    }
    private ResponseEntity<ErrorResponseDto> badRequest(Throwable throwable) {
        return ResponseEntity.badRequest().body(ErrorResponseDto.builder()
                .message(throwable.getMessage())
                .time(LocalDateTime.now())
                .build());
    }
}
