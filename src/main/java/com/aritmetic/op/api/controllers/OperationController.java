package com.aritmetic.op.api.controllers;

import com.aritmetic.op.api.dtos.OperationRequestDto;
import com.aritmetic.op.api.dtos.OperationResponseDto;
import com.aritmetic.op.api.exceptions.CustomException;
import com.aritmetic.op.api.services.ArithmeticOperation;
import com.aritmetic.op.api.services.CustomUserDetailsService;
import com.aritmetic.op.api.services.RandomStringService;
import com.aritmetic.op.api.types.ArithmeticOperationFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.aritmetic.op.api.Constants.INSUFFICIENT_BALANCE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/operation/v1")
public class OperationController {
    public final RandomStringService randomStringService;
    public final CustomUserDetailsService customUserDetailsService;

    @PostMapping()
    public ResponseEntity<OperationResponseDto> operation(@RequestBody OperationRequestDto operationRequestDto) {

        if (!customUserDetailsService.canPerformOperation(operationRequestDto.getOperationType())) {
            throw new CustomException(INSUFFICIENT_BALANCE);
        }

        ArithmeticOperation operation = ArithmeticOperationFactory.getArithmeticOperation(
                operationRequestDto.getOperationType());

        List<Double> operands = operationRequestDto.getOperands();
        operation.validateCalculation(operands);
        return customUserDetailsService.saveOperation(operationRequestDto.getOperationType(), operation.calculate(operands));

    }

}
