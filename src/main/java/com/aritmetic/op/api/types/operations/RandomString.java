package com.aritmetic.op.api.types.operations;

import com.aritmetic.op.api.dtos.OperationResponseDto;
import com.aritmetic.op.api.services.ArithmeticOperation;
import com.aritmetic.op.api.util.OperationDtoMapper;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class RandomString implements ArithmeticOperation {

    @Override
    public ResponseEntity<OperationResponseDto> calculate(List<Double> operands) {
        return OperationDtoMapper.handleSuccessResponseEntity(0.0, "");
    }

    @Override
    public void handleValidation(List<Double> operands) {

    }

}