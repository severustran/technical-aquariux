package com.aquariux.userservice.service;

import com.aquariux.userservice.dto.UserDto;
import com.aquariux.userservice.entity.UserEntity;
import com.aquariux.userservice.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;
    public UserDto getUser(Long userId) {
        UserEntity userEntity = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("Not found user by id %s" + userId));
        return objectMapper.convertValue(userEntity, UserDto.class);
    }
}
