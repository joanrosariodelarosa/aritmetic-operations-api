package com.aritmetic.op.api.services;

import com.aritmetic.op.api.dtos.OperationResponseDto;
import com.aritmetic.op.api.models.Operation;
import com.aritmetic.op.api.models.Record;
import com.aritmetic.op.api.models.User;
import com.aritmetic.op.api.repositories.RecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecordService {

    private final RecordRepository recordRepository;
    private final CustomUserDetailsService customUserDetailsService;

    public List<Record> getPaginatedRecords(User user, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return recordRepository.findAllByUserIdOrderByDateDesc(user.getId(), pageable);
    }

    public Record createAndSaveNewRecord(ResponseEntity<OperationResponseDto> result, Operation operation) {
        Record record = Record.builder()
                .userBalance(0.5)
                .userId(customUserDetailsService.getSecuredUser().getId())
                .operation(operation)
                .date(LocalDateTime.now())
                .amount(result.getBody().getResult())
                .userBalance(result.getBody().getCurrentBalance())
                .operationResponse(result.getBody().toString())
                .build();
        recordRepository.save(record);
        return record;
    }

    public double getLastBalanceFromListOfRecords() {
        List<Record> listOfRecords = getPaginatedRecords(customUserDetailsService.getSecuredUser(), 0, 1);
        return listOfRecords.size() != 0 ? listOfRecords.get(0).getUserBalance() : 0.0;
    }

}