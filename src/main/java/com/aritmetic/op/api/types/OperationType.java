package com.aritmetic.op.api.types;

import com.aritmetic.op.api.dtos.OperationResponseDto;
import com.aritmetic.op.api.services.ArithmeticOperation;
import com.aritmetic.op.api.types.operations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RequiredArgsConstructor
public enum OperationType {
    ADDITION(Addition.INSTANCE),
    SUBTRACTION(Subtraction.INSTANCE),
    MULTIPLICATION(Multiplication.INSTANCE),
    DIVISION(Division.INSTANCE),
    SQUARE_ROOT(SquareRoot.INSTANCE),
    RANDOM_STRING_GENERATION(RandomString.INSTANCE);
    private final ArithmeticOperation operation;

    public ResponseEntity<OperationResponseDto> performOperation(List<Double> operands) {
        return operation.performOperation(operands);
    }

    public void handleValidation(List<Double> operands) {
        operation.handleValidation(operands);
    }
}