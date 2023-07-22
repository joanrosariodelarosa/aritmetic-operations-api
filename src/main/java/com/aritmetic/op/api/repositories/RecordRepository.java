package com.aritmetic.op.api.repositories;

import com.aritmetic.op.api.models.Record;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RecordRepository extends JpaRepository<Record,Long> {
    @Query("SELECT r FROM Record r WHERE r.recordActive = true AND r.userId = :userId ORDER BY r.date DESC")
    Page<Record> findActiveRecordsByUserId(Long userId, Pageable pageable);

    @Query("SELECT COUNT(r) FROM Record r WHERE r.recordActive = true")
    Long countActiveRecords();

}
