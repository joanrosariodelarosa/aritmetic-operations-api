package com.aritmetic.op.api.exceptions;

import com.aritmetic.op.api.types.OperationType;

import java.util.List;

import static com.aritmetic.op.api.Constants.*;

public class ErrorMessageHandler {
    public static void validateErrorMessageByOperationType(List<Double> numbers, OperationType operationType) {
        String errorMessage = "";
        if (notContainOnlyNumbers(numbers)) {
            errorMessage = ONLY_DOUBLE_NUMBERS_ERROR_MESSAGE;
        } else if (numbers.size() > 2) {
            errorMessage = ONLY_TWO_NUMBERS_ALLOWED_MESSAGE;
        } else {
            switch (numbers.size()) {
                case 2:
                    errorMessage = validateTwoNumbers(numbers, operationType);
                    break;
                case 1:
                    errorMessage = validateSingleNumber(operationType);
                    break;
                default:
                    errorMessage = EMPTY_NUMBERS_ERROR_MESSAGE;
                    break;
            }
        }

        if (!errorMessage.isEmpty()) {
            throw new CustomException(errorMessage + " in " + operationType.toString().toLowerCase());
        }

    }

    private static boolean notContainOnlyNumbers(List<Double> numbers) {
        boolean isNaN = false;
        if (numbers.size() > 0) {
            for (Object number : numbers) {
                if (!(number instanceof Double)) {
                    return true;
                }
            }
        }
        return isNaN;
    }

    private static String validateTwoNumbers(List<Double> numbers, OperationType operationType) {
        double num1 = numbers.get(0);
        double num2 = numbers.get(1);
        String message = "";

        if (operationType == OperationType.SQUARE_ROOT) {
            message = ERROR_JUST_ONE_NUMBER;
        } else if ((num1 < 0 || num2 < 0) && operationType == OperationType.DIVISION) {
            message = ERROR_NEGATIVE_NUMBER;
        } else {
            ThrowdivideByZeroMessage(numbers, operationType);
        }

        return message;
    }

    private static String validateSingleNumber(OperationType operationType) {
        return operationType == OperationType.ADDITION ||
                operationType == OperationType.SUBTRACTION ||
                operationType == OperationType.MULTIPLICATION ? AT_LEAST_TWO_NUMBERS_ERROR : "";
    }

    static void ThrowdivideByZeroMessage(List<Double> numbers, OperationType operationType) {
        numbers.stream().skip(1).reduce(numbers.get(0), (a, b) -> {
            if (b == 0) {
                throw new CustomException(DIVIDE_BY_ZERO + " in " + operationType.toString().toLowerCase());
            }
            return a / b;
        });
    }

}
