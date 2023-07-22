package com.aritmetic.op.api.types.operations;

import com.aritmetic.op.api.dtos.OperationResponseDto;
import com.aritmetic.op.api.services.ArithmeticOperation;
import com.aritmetic.op.api.services.RandomStringService;
import com.aritmetic.op.api.util.OperationDtoMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class RandomString implements ArithmeticOperation {

    @Override
    public ResponseEntity<OperationResponseDto> calculate(List<Double> operands) {
        RandomStringService randomStringService = new RandomStringService();
        return OperationDtoMapper.handleSuccessOperationResponseEntity(0.0, randomStringService.getRandomString());
    }

    @Override
    public void validateCalculation(List<Double> operands) {
    }

}