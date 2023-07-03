package com.aritmetic.op.api.types;

import com.aritmetic.op.api.exceptions.CustomException;
import com.aritmetic.op.api.services.ArithmeticOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ArithmeticOperationFactory {
    static Logger logger = LoggerFactory.getLogger(ArithmeticOperationFactory.class);
    static String OPERATIONS_PATH = "com.aritmetic.op.api.types.operations.";

    public static ArithmeticOperation getArithmeticOperation(OperationType operationType) {

        try {
            ArithmeticOperation arithmeticOperation = (ArithmeticOperation) Class.forName(
                            OPERATIONS_PATH + operationType.toString()).getDeclaredConstructor()
                    .newInstance();
            logger.info("Arithmetic operation type supported! {}", operationType);
            return arithmeticOperation;
        } catch (Exception e) {
            throw new CustomException("Missing implementation for operation type: " + operationType);
        }
    }
}