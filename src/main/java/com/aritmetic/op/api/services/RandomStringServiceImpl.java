package com.aritmetic.op.api.services;

import com.aritmetic.op.api.dtos.OperationResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import static com.aritmetic.op.api.Constants.SUCCESSFULLY_OPERATION;

@Service
public class RandomStringServiceImpl implements RandomString {

    public ResponseEntity<OperationResponseDto> getRandomString(){
        String randomString = "";
        return ResponseEntity.ok(OperationResponseDto.builder()
                .success(true)
                .randomString(randomString)
                .errorMessage(SUCCESSFULLY_OPERATION)
                .build());
    }
}
