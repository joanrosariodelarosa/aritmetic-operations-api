package com.aritmetic.op.api.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "record")
public class Record {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "operation_id")
    private Operation operation;
    private double amount;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "user_balance")
    private double userBalance;
    @Column(name = "operation_response")
    private String operationResponse;
    private LocalDateTime date;
    @Column(name = "record_active")
    private boolean recordActive;

}




