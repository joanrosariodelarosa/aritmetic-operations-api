package com.aritmetic.op.api.types.operations;

import com.aritmetic.op.api.dtos.OperationResponseDto;
import com.aritmetic.op.api.services.ArithmeticOperation;
import com.aritmetic.op.api.util.OperationDtoMapper;
import com.aritmetic.op.api.util.OperationValidatorUtil;
import org.springframework.http.ResponseEntity;

import java.util.List;

public enum Division implements ArithmeticOperation {
    INSTANCE;

    @Override
    public ResponseEntity<OperationResponseDto> performOperation(List<Double> operands) {
        return OperationDtoMapper.handleSuccessResponseEntity(
                operands.stream().skip(1).reduce(operands.get(0), (a, b) -> a / b),"");
    }

    @Override
    public void handleValidation(List<Double> operands) {
        OperationValidatorUtil.basicOperandValidation(operands);
    }

}