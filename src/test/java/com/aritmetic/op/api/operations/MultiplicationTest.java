package com.aritmetic.op.api.operations;

import com.aritmetic.op.api.dtos.OperationResponseDto;
import com.aritmetic.op.api.exceptions.CustomException;
import com.aritmetic.op.api.types.operations.Multiplication;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static com.aritmetic.op.api.Constants.*;

public class MultiplicationTest {

    Multiplication multiplication;

    @BeforeEach
    void setup() {
        multiplication = new Multiplication();
    }

    @Test
    public void testCalculateMultiplication() {
        List<Double> operands = Arrays.asList(2.0, 3.5);
        ResponseEntity<OperationResponseDto> response = multiplication.calculate(operands);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(7.0, response.getBody().getOperationResult());
    }

    @Test
    public void invalidateNegativeNumbersInMultiplication() {
        List<Double> operands = Arrays.asList(2.0, -1.0);
        try {
            multiplication.validateCalculation(operands);
        } catch (CustomException c) {
            Assertions.assertEquals(ERROR_NEGATIVE_NUMBER, c.getMessage());
        }
    }

    @Test
    public void invalidateMissingOperandsInMultiplication() {
        List<Double> operands = Arrays.asList(2.0);
        try {
            multiplication.validateCalculation(operands);
        } catch (CustomException c) {
            Assertions.assertEquals(MISSING_NUMBERS_ERROR_MESSAGE, c.getMessage());
        }
    }

    @Test
    public void invalidateExtraOperandInMultiplication() {
        List<Double> operands = Arrays.asList(2.0, 2.0, 2.0);
        try {
            multiplication.validateCalculation(operands);
        } catch (CustomException c) {
            Assertions.assertEquals(ONLY_TWO_NUMBERS_ALLOWED_MESSAGE, c.getMessage());
        }
    }
}
