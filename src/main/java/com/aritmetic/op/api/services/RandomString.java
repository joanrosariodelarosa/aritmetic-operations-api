package com.aritmetic.op.api.services;

import com.aritmetic.op.api.dtos.OperationResponseDto;
import org.springframework.http.ResponseEntity;

public interface RandomString {
    ResponseEntity<OperationResponseDto> getRandomString();
}
