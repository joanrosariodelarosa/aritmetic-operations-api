package com.aritmetic.op.api.models;

import com.aritmetic.op.api.types.OperationType;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Data
@Entity
@Builder
@Table(name = "operation")
public class Operation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private OperationType operationType;
    private double cost;

}