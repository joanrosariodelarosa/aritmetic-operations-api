package com.aritmetic.op.api.types.operations;

import com.aritmetic.op.api.dtos.OperationResponseDto;
import com.aritmetic.op.api.services.ArithmeticOperation;
import com.aritmetic.op.api.util.OperationDtoMapper;
import com.aritmetic.op.api.util.OperationValidatorUtil;
import org.springframework.http.ResponseEntity;

import java.util.List;

public enum Subtraction implements ArithmeticOperation {
    INSTANCE;

    @Override
    public ResponseEntity<OperationResponseDto> performOperation(List<Double> operands) {
        return OperationDtoMapper.handleSuccessResponseEntity(
                operands.stream().reduce((a, b) -> a - b).orElse(0.0),"");
    }

    @Override
    public void handleValidation(List<Double> operands) {
        OperationValidatorUtil.basicOperandValidation(operands);
    }

}