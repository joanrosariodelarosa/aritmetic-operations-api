package com.aritmetic.op.api.services;

import com.aritmetic.op.api.dtos.OperationResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ArithmeticOperation {
    ResponseEntity<OperationResponseDto> performOperation(List<Double> operands);
    void handleValidation(List<Double> operands);
}
