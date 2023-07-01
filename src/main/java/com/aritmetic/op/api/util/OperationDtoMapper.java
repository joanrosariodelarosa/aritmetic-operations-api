package com.aritmetic.op.api.util;

import com.aritmetic.op.api.dtos.OperationResponseDto;
import org.springframework.http.ResponseEntity;

import static com.aritmetic.op.api.Constants.SUCCESSFULLY_OPERATION;

public class OperationDtoMapper {

    public static ResponseEntity<OperationResponseDto> handleSuccessResponseEntity(double operationResult,
                                                                                   String randomString) {
        return ResponseEntity.ok(OperationResponseDto.builder()
                .success(true)
                .result(operationResult)
                .randomString(randomString)
                .currentBalance(0.0)
                .errorMessage(SUCCESSFULLY_OPERATION)
                .build());
    }
}
