package com.aritmetic.op.api.operations;

import com.aritmetic.op.api.dtos.OperationResponseDto;
import com.aritmetic.op.api.exceptions.CustomException;
import com.aritmetic.op.api.types.operations.Division;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static com.aritmetic.op.api.Constants.*;

public class DivisionTest {

    Division division;

    @BeforeEach
    void setup() {
        division = new Division();
    }

    @Test
    public void testCalculateDivision() {
        List<Double> operands = Arrays.asList(5.0, 3.0);
        ResponseEntity<OperationResponseDto> response = division.calculate(operands);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(1.6666666666666667, response.getBody().getOperationResult());
    }

    @Test
    public void invalidateDivisionByZero() {
        List<Double> operands = Arrays.asList(2.0, 0.0);
        try {
            division.validateCalculation(operands);
        } catch (CustomException c) {
            Assertions.assertEquals(DIVIDE_BY_ZERO, c.getMessage());
        }
    }

    @Test
    public void invalidateNegativeNumbersInDivision() {
        List<Double> operands = Arrays.asList(2.0, -1.0);
        try {
            division.validateCalculation(operands);
        } catch (CustomException c) {
            Assertions.assertEquals(ERROR_NEGATIVE_NUMBER, c.getMessage());
        }
    }

    @Test
    public void invalidateMissingOperandsInDivision() {
        List<Double> operands = Arrays.asList(2.0);
        try {
            division.validateCalculation(operands);
        } catch (CustomException c) {
            Assertions.assertEquals(MISSING_NUMBERS_ERROR_MESSAGE, c.getMessage());
        }
    }

    @Test
    public void invalidateExtraOperandInDivision() {
        List<Double> operands = Arrays.asList(2.0, 2.0, 2.0);
        try {
            division.validateCalculation(operands);
        } catch (CustomException c) {
            Assertions.assertEquals(ONLY_TWO_NUMBERS_ALLOWED_MESSAGE, c.getMessage());
        }
    }
}
