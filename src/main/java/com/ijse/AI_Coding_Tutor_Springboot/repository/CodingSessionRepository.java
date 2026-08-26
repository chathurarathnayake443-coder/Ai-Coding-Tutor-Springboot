package com.ijse.AI_Coding_Tutor_Springboot.repository;

import com.ijse.AI_Coding_Tutor_Springboot.dto.SessionHistoryDTO;
import com.ijse.AI_Coding_Tutor_Springboot.entity.CodingSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CodingSessionRepository extends JpaRepository<CodingSession, Long> {

    @Query("SELECT COUNT(cs.sessionId) FROM CodingSession cs WHERE cs.student.studentId = ?1 AND cs.sessionStatus = 'COMPLETED'")
    long getCompletedSessionCount(long userId);

    @Query("SELECT new com.ijse.AI_Coding_Tutor_Springboot.dto.SessionHistoryDTO(" +
            "cs.sessionId,cs.problem.problemText, cs.programmingLanguage.languageName, COUNT(h), cs.startTime, " +
            "r.ratingValue, cs.sessionStatus) " +
            "FROM CodingSession cs " +
            "LEFT JOIN cs.hints h " +
            "LEFT JOIN cs.rating r " +
            "WHERE cs.student.studentId = ?1 " +
            "GROUP BY cs.sessionId, cs.problem.problemText, cs.programmingLanguage, cs.startTime, r.ratingValue, cs.sessionStatus")
    List<SessionHistoryDTO> getSessionHistory(long userId);
}
