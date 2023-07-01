package com.aritmetic.op.api.dtos;

import com.aritmetic.op.api.types.OperationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OperationRequestDto {
    private List<Double> operands;
    private OperationType operationType;

}
