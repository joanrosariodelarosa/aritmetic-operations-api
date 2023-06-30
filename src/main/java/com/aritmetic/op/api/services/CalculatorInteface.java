package com.aritmetic.op.api.services;

import com.aritmetic.op.api.dtos.OperationResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CalculatorInteface {
    ResponseEntity<OperationResponseDto> performAddition(List<Double> numbers) throws Exception;

    ResponseEntity<OperationResponseDto> performSubtraction(List<Double> numbers) throws Exception;

    ResponseEntity<OperationResponseDto> performMultiplication(List<Double> numbers) throws Exception;

    ResponseEntity<OperationResponseDto> performDivision(List<Double> numbers) throws Exception;

    ResponseEntity<OperationResponseDto> performSquareRoot(List<Double> numbers) throws Exception;

}
