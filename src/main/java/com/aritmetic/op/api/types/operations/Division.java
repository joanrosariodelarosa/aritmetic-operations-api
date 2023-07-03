package com.aritmetic.op.api.types.operations;

import com.aritmetic.op.api.dtos.OperationResponseDto;
import com.aritmetic.op.api.services.ArithmeticOperation;
import com.aritmetic.op.api.util.OperationDtoMapper;
import com.aritmetic.op.api.util.OperationValidatorUtil;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class Division implements ArithmeticOperation {
    @Override
    public ResponseEntity<OperationResponseDto> calculate(List<Double> operands) {
        return OperationDtoMapper.handleSuccessOperationResponseEntity(
                operands.stream().skip(1).reduce(operands.get(0), (a, b) -> a / b),"");
    }
    @Override
    public void validateCalculation(List<Double> operands) {
        OperationValidatorUtil.invalidateOperationWithOneOperand(operands);
        OperationValidatorUtil.basicOperandValidation(operands);
        OperationValidatorUtil.validateDivisionByZero(operands);
    }

}