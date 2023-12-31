package com.aritmetic.op.api.dtos;

import com.aritmetic.op.api.models.Record;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OperationResponseDto {
    private boolean success;
    private double operationResult;
    private String randomString;
    private double currentBalance;
    private String errorMessage;
    private Record recordCreated;

}
