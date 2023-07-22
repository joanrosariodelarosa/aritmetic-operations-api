package com.aritmetic.op.api.util;

import com.aritmetic.op.api.dtos.OperationResponseDto;
import com.aritmetic.op.api.dtos.RecordResponseDto;
import com.aritmetic.op.api.models.Record;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static com.aritmetic.op.api.Constants.SUCCESSFULLY_OPERATION;

public class OperationDtoMapper {

    public static ResponseEntity<OperationResponseDto> handleSuccessOperationResponseEntity(double operationResult,
                                                                                            String randomString) {
        return ResponseEntity.ok(OperationResponseDto.builder()
                .success(true)
                .operationResult(operationResult)
                .randomString(randomString)
                .currentBalance(0.0)
                .errorMessage(SUCCESSFULLY_OPERATION)
                .build());
    }

    public static ResponseEntity<RecordResponseDto> handleSuccessRecordResponseEntity(
            List<Record> listOfRecords, long totalRecords) {
        return ResponseEntity.ok(RecordResponseDto.builder()
                .listOfRecords(listOfRecords)
                .totalRecords(totalRecords)
                .build());
    }
}
