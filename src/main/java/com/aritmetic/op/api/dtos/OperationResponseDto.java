package com.aritmetic.op.api.dtos;

import com.aritmetic.op.api.models.Record;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OperationResponseDto {
    private boolean success;
    private double result;
    private String randomString;
    private double currentBalance;
    private String errorMessage;
    private List<Record> listRecords;

}
