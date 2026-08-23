package com.ijse.AI_Coding_Tutor_Springboot.repository;

import com.ijse.AI_Coding_Tutor_Springboot.entity.CodingSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CodingSessionRepository extends JpaRepository<CodingSession, Long> {

    @Query("SELECT COUNT(cs.sessionId) FROM CodingSession cs WHERE cs.student.studentId = ?1 AND cs.sessionStatus = 'COMPLETED'")
    long getCompletedSessionCount(long userId);
}
