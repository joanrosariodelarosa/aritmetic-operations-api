package com.aritmetic.op.api.controllers;

import com.aritmetic.op.api.dtos.OperationRequestDto;
import com.aritmetic.op.api.dtos.OperationResponseDto;
import com.aritmetic.op.api.services.ArithmeticOperation;
import com.aritmetic.op.api.services.CalculatorService;
import com.aritmetic.op.api.services.RandomStringService;
import com.aritmetic.op.api.types.ArithmeticOperationFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/operation/v1")
public class OperationController {
    private final CalculatorService calculatorService;
    private final RandomStringService randomStringService;

    @Autowired
    public OperationController(CalculatorService calculatorService, RandomStringService randomStringService) {
        this.randomStringService = randomStringService;
        this.calculatorService = calculatorService;
    }

    @PostMapping()
    public ResponseEntity<OperationResponseDto> operation(@RequestBody OperationRequestDto operationRequestDto) {
        ArithmeticOperation operation = ArithmeticOperationFactory.getArithmeticOperation(
                operationRequestDto.getOperationType());
        List<Double> operands = operationRequestDto.getOperands();
        operation.handleValidation(operands);
        return operation.calculate(operands);
    }

}
