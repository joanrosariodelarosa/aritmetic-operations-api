package com.aritmetic.op.api.services;

import com.aritmetic.op.api.dtos.OperationResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ArithmeticOperation {
    ResponseEntity<OperationResponseDto> calculate(List<Double> operands);
    void validateCalculation(List<Double> operands);
}
