package com.aritmetic.op.api.types.operations;

import com.aritmetic.op.api.dtos.OperationResponseDto;
import com.aritmetic.op.api.services.ArithmeticOperation;
import com.aritmetic.op.api.util.OperationDtoMapper;
import com.aritmetic.op.api.util.OperationValidatorUtil;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class Multiplication implements ArithmeticOperation {
    @Override
    public ResponseEntity<OperationResponseDto> calculate(List<Double> operands) {
        return OperationDtoMapper.handleSuccessOperationResponseEntity(
                operands.stream().reduce(1.0, (a, b) -> a * b),"");
    }
    @Override
    public void validateCalculation(List<Double> operands) {
        OperationValidatorUtil.invalidateOperationWithOneOperand(operands);
        OperationValidatorUtil.basicOperandValidation(operands);
    }

}