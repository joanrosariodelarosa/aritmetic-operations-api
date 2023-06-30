package com.aritmetic.op.api.controllers;


import com.aritmetic.op.api.dtos.OperationResponseDto;
import com.aritmetic.op.api.services.CalculatorServiceImpl;
import com.aritmetic.op.api.services.RandomStringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/operation")
public class OperationController {
    private final CalculatorServiceImpl calculatorService;
    private final RandomStringService randomStringService;

    @Autowired
    public OperationController(CalculatorServiceImpl calculatorService, RandomStringService randomStringService) {
        this.randomStringService = randomStringService;
        this.calculatorService = calculatorService;
    }

    @PostMapping("/v1/addition")
    public ResponseEntity<OperationResponseDto> addition(@RequestBody List<Double> numbers) {
        return this.calculatorService.performAddition(numbers);
    }

    @PostMapping("/v1/subtraction")
    public ResponseEntity<OperationResponseDto> subtraction(@RequestBody List<Double> numbers) {
        return this.calculatorService.performSubtraction(numbers);
    }

    @PostMapping("/v1/multiplication")
    public ResponseEntity<OperationResponseDto> multiplication(@RequestBody List<Double> numbers) {
        return this.calculatorService.performMultiplication(numbers);
    }

    @PostMapping("/v1/division")
    public ResponseEntity<OperationResponseDto> division(@RequestBody List<Double> numbers) {
        return this.calculatorService.performDivision(numbers);
    }

    @PostMapping("/v1/squareRoot")
    public ResponseEntity<OperationResponseDto> squareRoot(@RequestBody List<Double> numbers) {
        return this.calculatorService.performSquareRoot(numbers);
    }

    @GetMapping("/v1/randomString")
    public ResponseEntity<OperationResponseDto> getRandomString() {
        return this.randomStringService.getRandomString();
    }
}
