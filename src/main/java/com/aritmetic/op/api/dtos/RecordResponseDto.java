package com.aritmetic.op.api.dtos;

import com.aritmetic.op.api.models.Record;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RecordResponseDto {
    private List<Record> listOfRecords;
    private long totalRecords;
}
