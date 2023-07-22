package com.aritmetic.op.api.operations;

import com.aritmetic.op.api.dtos.OperationResponseDto;
import com.aritmetic.op.api.exceptions.CustomException;
import com.aritmetic.op.api.types.operations.Subtraction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static com.aritmetic.op.api.Constants.*;

public class SubtractionTest {

    Subtraction subtraction;

    @BeforeEach
    void setup() {
        subtraction = new Subtraction();
    }

    @Test
    public void testCalculateSubtraction() {
        List<Double> operands = Arrays.asList(2.0, 3.5);
        ResponseEntity<OperationResponseDto> response = subtraction.calculate(operands);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(-1.5, response.getBody().getOperationResult());
    }

    @Test
    public void invalidateNegativeNumbersInSubtraction() {
        List<Double> operands = Arrays.asList(2.0, -1.0);
        try {
            subtraction.validateCalculation(operands);
        } catch (CustomException c) {
            Assertions.assertEquals(ERROR_NEGATIVE_NUMBER, c.getMessage());
        }
    }

    @Test
    public void invalidateMissingOperandsInSubtraction() {
        List<Double> operands = Arrays.asList(2.0);
        try {
            subtraction.validateCalculation(operands);
        } catch (CustomException c) {
            Assertions.assertEquals(MISSING_NUMBERS_ERROR_MESSAGE, c.getMessage());
        }
    }

    @Test
    public void invalidateExtraOperandInSubtraction() {
        List<Double> operands = Arrays.asList(2.0, 2.0, 2.0);
        try {
            subtraction.validateCalculation(operands);
        } catch (CustomException c) {
            Assertions.assertEquals(ONLY_TWO_NUMBERS_ALLOWED_MESSAGE, c.getMessage());
        }
    }
}
