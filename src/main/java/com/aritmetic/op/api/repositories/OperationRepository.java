package com.aritmetic.op.api.repositories;

import com.aritmetic.op.api.models.Operation;
import com.aritmetic.op.api.types.OperationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OperationRepository extends JpaRepository<Operation,Long> {
    Optional<List<Operation>> findByOperationType(OperationType operationType);
}
