package com.aritmetic.op.api.types.operations;

import com.aritmetic.op.api.dtos.OperationResponseDto;
import com.aritmetic.op.api.services.ArithmeticOperation;
import com.aritmetic.op.api.util.OperationDtoMapper;
import com.aritmetic.op.api.util.OperationValidatorUtil;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class Subtraction implements ArithmeticOperation {

    @Override
    public ResponseEntity<OperationResponseDto> calculate(List<Double> operands) {
        return OperationDtoMapper.handleSuccessResponseEntity(
                operands.stream().reduce((a, b) -> a - b).orElse(0.0), "");
    }

    @Override
    public void operationValidation(List<Double> operands) {
        OperationValidatorUtil.invalidateOperationWithOneOperand(operands);
        OperationValidatorUtil.basicOperandValidation(operands);
    }

}