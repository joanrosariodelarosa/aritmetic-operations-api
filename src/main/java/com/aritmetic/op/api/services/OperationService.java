package com.aritmetic.op.api.services;

import com.aritmetic.op.api.dtos.OperationResponseDto;
import com.aritmetic.op.api.models.Operation;
import com.aritmetic.op.api.models.Record;
import com.aritmetic.op.api.repositories.OperationRepository;
import com.aritmetic.op.api.types.OperationType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OperationService {
    private final RecordService recordService;
    private final OperationRepository operationRepository;

    public boolean canPerformOperation(OperationType operationType) {
        double lastBalance = recordService.getLastBalanceFromListOfRecords();
        double operationCost = getOperationCost(operationType);
        return lastBalance >= operationCost;
    }

    public double getOperationCost(OperationType operationType) {
        return getOperation(operationType).getCost();
    }

    public Operation getOperation(OperationType operationType) {
        Operation operation = null;
        Optional<List<Operation>> operationList = operationRepository.findByOperationType(operationType);
        if (operationList.isPresent()) {
            operation = operationList.get().get(0);
        }
        return operation;
    }

    public ResponseEntity<OperationResponseDto> saveOperation(OperationType operationType,
                                                              ResponseEntity<OperationResponseDto> result) {
        double operationCost = getOperationCost(operationType);
        double lastBalance = recordService.getLastBalanceFromListOfRecords();
        result.getBody().setCurrentBalance(lastBalance - operationCost);
        Record record = recordService.createAndSaveNewRecord(result, getOperation(operationType));
        result.getBody().setRecordCreated(record);
        return result;
    }
}
