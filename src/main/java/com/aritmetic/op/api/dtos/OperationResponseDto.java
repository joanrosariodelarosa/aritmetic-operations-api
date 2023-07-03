package com.aritmetic.op.api.dtos;

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
    private double result;
    private String randomString;
    private double currentBalance;
    private String errorMessage;

}
