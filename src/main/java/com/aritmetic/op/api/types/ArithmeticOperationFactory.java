package com.aritmetic.op.api.types;

import com.aritmetic.op.api.exceptions.CustomException;
import com.aritmetic.op.api.services.ArithmeticOperation;

public class ArithmeticOperationFactory {
    static String OPERATIONS_PATH = "com.aritmetic.op.api.types.operations.";

    public static ArithmeticOperation getArithmeticOperation(OperationType operationType) {
        try {
            return (ArithmeticOperation) Class.forName(OPERATIONS_PATH + operationType.toString())
                    .getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new CustomException("Missing implementation for operation type: " + operationType);
        }
    }
}