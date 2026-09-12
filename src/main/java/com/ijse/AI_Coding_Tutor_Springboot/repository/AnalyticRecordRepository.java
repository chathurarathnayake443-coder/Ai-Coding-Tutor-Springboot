package com.ijse.AI_Coding_Tutor_Springboot.repository;

import com.ijse.AI_Coding_Tutor_Springboot.entity.AnalyticRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AnalyticRecordRepository extends JpaRepository<AnalyticRecord, Long> {

    @Query("SELECT a FROM AnalyticRecord a WHERE a.student.studentId = ?1")
    Optional<AnalyticRecord> findAnalyticRecordByStudentId(long studentId);
}
