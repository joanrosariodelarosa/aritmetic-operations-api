package com.aritmetic.op.api.util;

import com.aritmetic.op.api.exceptions.CustomException;

import java.util.List;

import static com.aritmetic.op.api.Constants.*;

public class OperationValidatorUtil {

    public static String validateEmptyOperand(List<Double> operands) {
        return operands == null || operands.size() == 0 ? EMPTY_NUMBERS_ERROR_MESSAGE : "";
    }

    public static void validateDivisionByZero(List<Double> numbers) {
        numbers.stream().skip(1).reduce(numbers.get(0), (a, b) -> {
            if (b == 0) {
                throw new CustomException(DIVIDE_BY_ZERO);
            }
            return a / b;
        });
    }

    public boolean containTwoOperands(List<Double> operands) {
        return !(validateEmptyOperand(operands).length() > 0) && operands.size() == 2;
    }

    public static boolean containOneOperand(List<Double> operands) {
        return !(validateEmptyOperand(operands).length() > 0) && operands.size() == 1;
    }

    public static void basicOperandValidation(List<Double> operands) {
        String error = notContainOnlyNumbers(operands);
        if (containOneOperand(operands)) {
            error = EMPTY_NUMBERS_ERROR_MESSAGE;
        } else if (!(validateEmptyOperand(operands).length() > 0) && operands.size() > 2) {
            error = ONLY_TWO_NUMBERS_ALLOWED_MESSAGE;
        }
        if (error.length() > 0) {
            throw new CustomException(error);
        }
    }

    private String validateNegativeNumbers(List<Double> numbers) {
        return (numbers.get(0) < 0 || numbers.get(1) < 0) ? ERROR_NEGATIVE_NUMBER : "";
    }

    private static String notContainOnlyNumbers(List<Double> numbers) {
        String error = "";
        for (Object number : numbers) {
            if (!(number instanceof Double)) {
                return ONLY_DOUBLE_NUMBERS_ERROR_MESSAGE;
            }
        }
        return error;
    }

}
