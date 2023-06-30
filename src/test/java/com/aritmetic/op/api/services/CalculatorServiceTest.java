package com.aritmetic.op.api.services;

import com.aritmetic.op.api.dtos.OperationResponseDto;
import com.aritmetic.op.api.exceptions.CustomException;
import com.aritmetic.op.api.types.OperationType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static com.aritmetic.op.api.Constants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class CalculatorServiceTest {

    @InjectMocks
    private CalculatorServiceImpl calculatorServiceImpl;

    @InjectMocks
    private RandomStringService randomStringService;


    @BeforeEach
    private void setup() {
        calculatorServiceImpl = new CalculatorServiceImpl(randomStringService);
    }

    @ParameterizedTest
    @MethodSource("operationTestData")
    void testSuccessfullyOperationAndOkStatusCode(Operation operation, List<Double> numbers, double expectedValue) {
        ResponseEntity<OperationResponseDto> response = operation.performOperation(calculatorServiceImpl, numbers);
        assertEquals(buildExpectedResponse(expectedValue, 0.0), response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    private static Stream<Arguments> operationTestData() {
        return Stream.of(
                Arguments.of(Operation.ADDITION, Arrays.asList(1.5, 2.5), 4.0),
                Arguments.of(Operation.SUBTRACTION, Arrays.asList(1.5, 2.5), -1.0),
                Arguments.of(Operation.DIVISION, Arrays.asList(1.5, 2.5), 0.6),
                Arguments.of(Operation.MULTIPLICATION, Arrays.asList(1.5, 2.5), 3.75),
                Arguments.of(Operation.SQUARE_ROOT, Arrays.asList(4.0), 2.0)
        );
    }

    enum Operation {
        ADDITION {
            @Override
            ResponseEntity<OperationResponseDto> performOperation(CalculatorServiceImpl calculatorService, List<Double> numbers) {
                return calculatorService.performAddition(numbers);
            }
        },
        SUBTRACTION {
            @Override
            ResponseEntity<OperationResponseDto> performOperation(CalculatorServiceImpl calculatorService, List<Double> numbers) {
                return calculatorService.performSubtraction(numbers);
            }
        },
        DIVISION {
            @Override
            ResponseEntity<OperationResponseDto> performOperation(CalculatorServiceImpl calculatorService, List<Double> numbers) {
                return calculatorService.performDivision(numbers);
            }
        },
        MULTIPLICATION {
            @Override
            ResponseEntity<OperationResponseDto> performOperation(CalculatorServiceImpl calculatorService, List<Double> numbers) {
                return calculatorService.performMultiplication(numbers);
            }
        },
        SQUARE_ROOT {
            @Override
            ResponseEntity<OperationResponseDto> performOperation(CalculatorServiceImpl calculatorService, List<Double> numbers) {
                return calculatorService.performSquareRoot(numbers);
            }
        };

        abstract ResponseEntity<OperationResponseDto> performOperation(CalculatorServiceImpl calculatorService, List<Double> numbers);
    }

    @ParameterizedTest
    @MethodSource("genericErrorMessages")
    void testAdditionOperationErrorMessages(List<Double> numbers, String expectedErrorMessage) {
        try {
            calculatorServiceImpl.performAddition(numbers);
        } catch (CustomException e) {
            assertErrorMessageByOperationType(OperationType.ADDITION, expectedErrorMessage, e);
        }
    }

    @ParameterizedTest
    @MethodSource("genericErrorMessages")
    void testSubtractionOperationErrorMessages(List<Double> numbers, String expectedErrorMessage) {
        try {
            calculatorServiceImpl.performSubtraction(numbers);
        } catch (CustomException e) {
            assertErrorMessageByOperationType(OperationType.SUBTRACTION, expectedErrorMessage, e);
        }
    }

    @ParameterizedTest
    @MethodSource({"genericErrorMessages"})
    void testMultiplicationOperationErrorMessages(List<Double> numbers, String expectedErrorMessage) {
        try {
            calculatorServiceImpl.performMultiplication(numbers);
        } catch (CustomException e) {
            assertErrorMessageByOperationType(OperationType.MULTIPLICATION, expectedErrorMessage, e);
        }
    }

    @ParameterizedTest
    @MethodSource({"divisionErrorMessages", "genericErrorMessages"})
    void testDivisionOperationErrorMessages(List<Double> numbers, String expectedErrorMessage) {
        try {
            calculatorServiceImpl.performDivision(numbers);
        } catch (CustomException e) {
            assertErrorMessageByOperationType(OperationType.DIVISION, expectedErrorMessage, e);
        }
    }

    @ParameterizedTest
    @MethodSource({"squareRootErrorMessages", "genericErrorMessages"})
    void testSquareRootOperationErrorMessages(List<Double> numbers, String expectedErrorMessage) {
        try {
            calculatorServiceImpl.performSquareRoot(numbers);
        } catch (CustomException e) {
            assertErrorMessageByOperationType(OperationType.SQUARE_ROOT, expectedErrorMessage, e);
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

    private void assertErrorMessageByOperationType(OperationType operationType,
                                                   String expectedErrorMessage, CustomException e) {
        Assertions.assertEquals(expectedErrorMessage + " in "
                + operationType.toString().toLowerCase(), e.getMessage());
    }

    private OperationResponseDto buildExpectedResponse(double result, double currentBalance) {
        return OperationResponseDto.builder()
                .success(true)
                .result(result)
                .currentBalance(currentBalance)
                .errorMessage(SUCCESSFULLY_OPERATION)
                .build();
    }
}