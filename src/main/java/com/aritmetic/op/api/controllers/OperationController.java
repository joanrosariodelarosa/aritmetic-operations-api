package com.aritmetic.op.api.controllers;

import com.aritmetic.op.api.dtos.OperationRequestDto;
import com.aritmetic.op.api.dtos.OperationResponseDto;
import com.aritmetic.op.api.exceptions.CustomException;
import com.aritmetic.op.api.services.ArithmeticOperation;
import com.aritmetic.op.api.services.CustomUserDetailsService;
import com.aritmetic.op.api.types.ArithmeticOperationFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/operation/v1")
public class OperationController {
    public final CustomUserDetailsService customUserDetailsService;

    @PostMapping()
    public ResponseEntity<OperationResponseDto> operation(@RequestBody OperationRequestDto operationRequestDto) {
        ArithmeticOperation operation = ArithmeticOperationFactory.getArithmeticOperation(
                operationRequestDto.getOperationType());
        List<Double> operands = operationRequestDto.getOperands();
        if (!customUserDetailsService.canPerformOperation(operationRequestDto)) {
            throw new CustomException("Insufficient Balance for this operation!");
        }
        operation.operationValidation(operands);
        ResponseEntity<OperationResponseDto> res = operation.calculate(operands);
        return customUserDetailsService.save(operationRequestDto, res);
    }

}
