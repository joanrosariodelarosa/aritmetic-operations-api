package com.aritmetic.op.api.util;

import com.aritmetic.op.api.Constants;
import com.aritmetic.op.api.exceptions.CustomException;
import com.aritmetic.op.api.services.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.aritmetic.op.api.Constants.*;

@RequiredArgsConstructor
@Component
public class OperationValidatorUtil {

    private final CustomUserDetailsService customUserDetailsService;

    public static void validateDivisionByZero(List<Double> numbers) {
        numbers.stream().skip(1).reduce(numbers.get(0), (a, b) -> {
            if (b == 0) {
                throw new CustomException(DIVIDE_BY_ZERO);
            }
            return a / b;
        });
    }

    public static String missingOperandsInList(List<Double> operands) {
        return operands == null || operands.size() == 0 ? MISSING_NUMBERS_ERROR_MESSAGE : "";
    }

    public static boolean invalidNumberOfOperands(List<Double> operands) {
        return !(missingOperandsInList(operands).length() > 0) && operands.size() > 2;
    }

    public static boolean containOneOperand(List<Double> operands) {
        return !(missingOperandsInList(operands).length() > 0) && operands.size() == 1;
    }

    public static void invalidateOperationWithOneOperand(List<Double> operands) {
        if (OperationValidatorUtil.containOneOperand(operands)) {
            throw (new CustomException(Constants.MISSING_NUMBERS_ERROR_MESSAGE));
        }
    }

    public static void basicOperandValidation(List<Double> operands) {
        String error = validateOnlyNumberOperands(operands);
        if (error.length() == 0) {
            if (invalidNumberOfOperands(operands)) {
                error = ONLY_TWO_NUMBERS_ALLOWED_MESSAGE;
            } else if (hasNegativeOperands(operands)) {
                error = ERROR_NEGATIVE_NUMBER;
            }
        }
        if (error.length() > 0) {
            throw new CustomException(error);
        }
    }

    public static boolean hasNegativeOperands(List<Double> numbers) {
        return !(missingOperandsInList(numbers).length() > 0) && numbers.stream().anyMatch(n -> n < 0);
    }

    private static String validateOnlyNumberOperands(List<Double> numbers) {
        String error = "";
        for (Object number : numbers) {
            if (!(number instanceof Double)) {
                return ONLY_DOUBLE_NUMBERS_ERROR_MESSAGE;
            }
        }
        return error;
    }

}
