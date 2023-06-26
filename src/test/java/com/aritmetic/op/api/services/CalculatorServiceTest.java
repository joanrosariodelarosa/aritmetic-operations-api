package com.aritmetic.op.api.services;

import com.aritmetic.op.api.dtos.OperationResponseDto;
import com.aritmetic.op.api.exceptions.CalculatorException;
import com.aritmetic.op.api.types.OperationType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static com.aritmetic.op.api.Constants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class CalculatorServiceTest {

    @InjectMocks
    private CalculatorServiceImpl calculatorServiceImpl;

    @InjectMocks
    private RandomStringServiceImpl randomStringService;

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @BeforeEach
    private void setup() {
        calculatorServiceImpl = new CalculatorServiceImpl(randomStringService, userServiceImpl);
    }

    @Test
    void testSuccessfullyAdditionOperationAndStatusCode() {

        ResponseEntity<OperationResponseDto> result = calculatorServiceImpl
                .performAddition(Arrays.asList(1.5, 2.5));

        assertEquals(getFinalResponse(4.0, 0.0, null), result.getBody());
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @ParameterizedTest
    @MethodSource("genericErrorMessages")
    void testAdditionOperationErrorMessages(List<Double> numbers, String expectedErrorMessage) {
        try {
            calculatorServiceImpl.performAddition(numbers);
        } catch (CalculatorException e) {
            assertMessageByOperationType(OperationType.ADDITION, expectedErrorMessage, e);
        }
    }
    @ParameterizedTest
    @MethodSource("genericErrorMessages")
    void testSubtractionOperationErrorMessages(List<Double> numbers, String expectedErrorMessage) {
        try {
            calculatorServiceImpl.performSubtraction(numbers);
        } catch (CalculatorException e) {
            assertMessageByOperationType(OperationType.SUBTRACTION, expectedErrorMessage, e);
        }
    }

    @ParameterizedTest
    @MethodSource({"genericErrorMessages"})
    void testMultiplicationOperationErrorMessages(List<Double> numbers, String expectedErrorMessage) {
        try {
            calculatorServiceImpl.performMultiplication(numbers);
        } catch (CalculatorException e) {
            assertMessageByOperationType(OperationType.MULTIPLICATION, expectedErrorMessage, e);
        }
    }
    @ParameterizedTest
    @MethodSource({"divisionErrorMessages", "genericErrorMessages"})
    void testDivisionOperationErrorMessages(List<Double> numbers, String expectedErrorMessage) {
        try {
            calculatorServiceImpl.performDivision(numbers);
        } catch (CalculatorException e) {
            assertMessageByOperationType(OperationType.DIVISION, expectedErrorMessage, e);
        }
    }
    @ParameterizedTest
    @MethodSource({"squareRootErrorMessages", "genericErrorMessages"})
    void testSquareRootOperationErrorMessages(List<Double> numbers, String expectedErrorMessage) {
        try {
            calculatorServiceImpl.performSquareRoot(numbers);
        } catch (CalculatorException e) {
            assertMessageByOperationType(OperationType.SQUARE_ROOT, expectedErrorMessage, e);
        }
    }

    private static List<Object[]> genericErrorMessages() {
        return Arrays.asList(
                new Object[]{Arrays.asList(1.5), AT_LEAST_TWO_NUMBERS_ERROR},
                new Object[]{Arrays.asList(2.0, ""), ONLY_DOUBLE_NUMBERS_ERROR_MESSAGE},
                new Object[]{Arrays.asList(), EMPTY_NUMBERS_ERROR_MESSAGE},
                new Object[]{Arrays.asList(2.0, 2.0, 2.0), ONLY_TWO_NUMBERS_ALLOWED_MESSAGE},
                new Object[]{Arrays.asList(2.0, 2), ONLY_DOUBLE_NUMBERS_ERROR_MESSAGE},
                new Object[]{Arrays.asList(5, 0.0), ONLY_DOUBLE_NUMBERS_ERROR_MESSAGE});
    }
    private static List<Object[]> divisionErrorMessages() {
        return Arrays.asList(
                new Object[]{Arrays.asList(5.0, 0.0), DIVIDE_BY_ZERO},
                new Object[]{Arrays.asList(5, 0.0), ONLY_DOUBLE_NUMBERS_ERROR_MESSAGE});
    }
    private static List<Object[]> squareRootErrorMessages() {
        return Arrays.asList(
                new Object[]{Arrays.asList(5.0, 0.0), ERROR_JUST_ONE_NUMBER},
                new Object[]{Arrays.asList(5, 0.0), ONLY_DOUBLE_NUMBERS_ERROR_MESSAGE});
    }

    private void assertMessageByOperationType(OperationType operationType,
                                              String expectedErrorMessage, CalculatorException e) {
        Assertions.assertEquals(expectedErrorMessage + " in "
                + operationType.toString().toLowerCase(), e.getMessage());
    }

    private OperationResponseDto getFinalResponse(double result, double currentBalance, String randomString) {
        return OperationResponseDto.builder()
                .success(true)
                .result(result)
                .currentBalance(currentBalance)
                .randomString(randomString)
                .errorMessage(SUCCESSFULLY_OPERATION)
                .build();
    }
}