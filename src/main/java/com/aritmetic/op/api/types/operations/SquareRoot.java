package com.aritmetic.op.api.types.operations;

import com.aritmetic.op.api.dtos.OperationResponseDto;
import com.aritmetic.op.api.services.ArithmeticOperation;
import com.aritmetic.op.api.util.OperationDtoMapper;
import org.springframework.http.ResponseEntity;

import java.util.List;

public enum SquareRoot implements ArithmeticOperation {
    INSTANCE;

    @Override
    public ResponseEntity<OperationResponseDto> performOperation(List<Double> operands) {
        return OperationDtoMapper.handleSuccessResponseEntity(Math.sqrt(operands.get(0)),"");
    }

    @Override
    public void handleValidation(List<Double> operands) {


    }

}