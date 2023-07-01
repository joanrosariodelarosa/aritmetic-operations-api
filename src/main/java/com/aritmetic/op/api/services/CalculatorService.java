package com.aritmetic.op.api.services;

import com.aritmetic.op.api.dtos.OperationResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import static com.aritmetic.op.api.Constants.*;

@Service
public class CalculatorService {

    private final RandomStringService randomStringService;


    @Autowired
    public CalculatorService(RandomStringService randomStringService) {
        this.randomStringService = randomStringService;
    }
//    public ResponseEntity<OperationResponseDto> performAddition(List<Double> numbers) {
//        ErrorMessageHandler.validateErrorMessageByOperationType(numbers, OperationType.ADDITION);
//        return handleSuccessResponseEntity(numbers.stream().mapToDouble(Double::doubleValue).sum());
//    }
//    public ResponseEntity<OperationResponseDto> performSubtraction(List<Double> numbers) {
//        ErrorMessageHandler.validateErrorMessageByOperationType(numbers, OperationType.SUBTRACTION);
//        return handleSuccessResponseEntity(numbers.stream().reduce((a, b) -> a - b).orElse(0.0));
//    }
//    public ResponseEntity<OperationResponseDto> performMultiplication(List<Double> numbers) {
//        ErrorMessageHandler.validateErrorMessageByOperationType(numbers, OperationType.MULTIPLICATION);
//        return handleSuccessResponseEntity(numbers.stream().reduce(1.0, (a, b) -> a * b));
//    }
//    public ResponseEntity<OperationResponseDto> performDivision(List<Double> numbers) {
//        ErrorMessageHandler.validateErrorMessageByOperationType(numbers, OperationType.DIVISION);
//        return handleSuccessResponseEntity(numbers.stream().skip(1).reduce(numbers.get(0), (a, b) -> a / b));
//    }
//    public ResponseEntity<OperationResponseDto> performSquareRoot(List<Double> numbers) {
//        ErrorMessageHandler.validateErrorMessageByOperationType(numbers, OperationType.SQUARE_ROOT);
//        return handleSuccessResponseEntity(Math.sqrt(numbers.get(0)));
//    }
    private ResponseEntity<OperationResponseDto> handleSuccessResponseEntity(double operationResult) {
        return ResponseEntity.ok(OperationResponseDto.builder()
                .success(true)
                .result(operationResult)
                .currentBalance(0.0)
                .errorMessage(SUCCESSFULLY_OPERATION)
                .build());
    }

}