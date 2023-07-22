package com.aritmetic.op.api.controllers;

import com.aritmetic.op.api.dtos.OperationRequestDto;
import com.aritmetic.op.api.dtos.OperationResponseDto;
import com.aritmetic.op.api.dtos.RecordRequestDto;
import com.aritmetic.op.api.dtos.RecordResponseDto;
import com.aritmetic.op.api.exceptions.CustomException;
import com.aritmetic.op.api.models.Record;
import com.aritmetic.op.api.services.ArithmeticOperation;
import com.aritmetic.op.api.services.CustomUserDetailsService;
import com.aritmetic.op.api.services.OperationService;
import com.aritmetic.op.api.services.RecordService;
import com.aritmetic.op.api.types.ArithmeticOperationFactory;
import com.aritmetic.op.api.util.OperationDtoMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.aritmetic.op.api.Constants.INSUFFICIENT_BALANCE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/operation")
public class OperationController {
    Logger logger = LoggerFactory.getLogger(OperationController.class);
    public final OperationService operationService;
    public final RecordService recordService;
    public final CustomUserDetailsService customUserDetailsService;

    @PostMapping("/v1")
    public ResponseEntity<OperationResponseDto> operation(@RequestBody OperationRequestDto operationRequestDto) {

        if (!operationService.canPerformOperation(operationRequestDto.getOperationType())) {
            throw new CustomException(INSUFFICIENT_BALANCE);
        }
        logger.info("Operation process started status: ok");
        ArithmeticOperation operation = ArithmeticOperationFactory.getArithmeticOperation(
                operationRequestDto.getOperationType());

        List<Double> operands = operationRequestDto.getOperands();
        operation.validateCalculation(operands);
        return operationService.saveOperation(operationRequestDto.getOperationType(), operation.calculate(operands));
    }

    @PostMapping("/records/v1")
    public ResponseEntity<RecordResponseDto> records(@RequestBody RecordRequestDto operationRequestDto) {
        if (operationRequestDto.getAction().equals("remove")) {
            recordService.removeRecord(operationRequestDto.getRecordId());
        }
        List<Record> allRecords = recordService.getPaginatedRecords(customUserDetailsService.getSecuredUser(),
                operationRequestDto.getRecordPage(), operationRequestDto.getRecordSize());
        long totalRecords = recordService.getTotalRecords();
        return OperationDtoMapper.handleSuccessRecordResponseEntity(allRecords, totalRecords);
    }

}
