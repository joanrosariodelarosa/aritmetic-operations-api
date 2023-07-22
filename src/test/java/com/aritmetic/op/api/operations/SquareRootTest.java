package com.aritmetic.op.api.operations;

import com.aritmetic.op.api.dtos.OperationResponseDto;
import com.aritmetic.op.api.exceptions.CustomException;
import com.aritmetic.op.api.types.operations.SquareRoot;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static com.aritmetic.op.api.Constants.*;

public class SquareRootTest {

    SquareRoot squareRoot;

    @BeforeEach
    void setup() {
        squareRoot = new SquareRoot();
    }

    @Test
    public void testCalculateSquareRoot() {
        List<Double> operands = Arrays.asList(2.0);
        ResponseEntity<OperationResponseDto> response = squareRoot.calculate(operands);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(1.4142135623730951, response.getBody().getOperationResult());
    }

    @Test
    public void invalidateExtraOperandInSquareRoot() {
        List<Double> operands = Arrays.asList(2.0, 2.0);
        try {
            squareRoot.validateCalculation(operands);
        } catch (CustomException c) {
            Assertions.assertEquals(ERROR_JUST_ONE_NUMBER, c.getMessage());
        }
    }

    @Test
    public void invalidateNegativeNumbersInSquareRoot() {
        List<Double> operands = Arrays.asList(2.0, -1.0);
        try {
            squareRoot.validateCalculation(operands);
        } catch (CustomException c) {
            Assertions.assertEquals(ERROR_NEGATIVE_NUMBER, c.getMessage());
        }
    }


}
