package com.aritmetic.op.api.models;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Builder
@Table(name = "record")
public class Record {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "operation_id")
    private Long operationId;
    @Column(name = "user_id")
    private Long userId;
    private double amount;
    @Column(name = "user_balance")
    private double userBalance;
    @Column(name = "operation_response")
    private String operationResponse;
    private LocalDateTime date;
    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;
}




