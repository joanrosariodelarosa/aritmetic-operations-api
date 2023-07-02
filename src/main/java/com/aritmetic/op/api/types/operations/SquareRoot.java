package com.aritmetic.op.api.types.operations;

import com.aritmetic.op.api.dtos.OperationResponseDto;
import com.aritmetic.op.api.exceptions.CustomException;
import com.aritmetic.op.api.services.ArithmeticOperation;
import com.aritmetic.op.api.util.OperationDtoMapper;
import com.aritmetic.op.api.util.OperationValidatorUtil;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static com.aritmetic.op.api.Constants.ERROR_JUST_ONE_NUMBER;

public class SquareRoot implements ArithmeticOperation {
    @Override
    public ResponseEntity<OperationResponseDto> calculate(List<Double> operands) {
        return OperationDtoMapper.handleSuccessResponseEntity(Math.sqrt(operands.get(0)), "");
    }

    @Override
    public void operationValidation(List<Double> operands) {
        OperationValidatorUtil.basicOperandValidation(operands);
        if (!OperationValidatorUtil.containOneOperand(operands)) {
            throw new CustomException(ERROR_JUST_ONE_NUMBER);
        }
    }

}