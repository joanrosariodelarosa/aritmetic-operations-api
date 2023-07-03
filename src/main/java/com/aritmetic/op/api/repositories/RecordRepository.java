package com.aritmetic.op.api.repositories;

import com.aritmetic.op.api.models.Record;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecordRepository extends JpaRepository<Record,Long> {
    List<Record> findAllByUserIdOrderByDateDesc(Long userId, Pageable pageable);

}
