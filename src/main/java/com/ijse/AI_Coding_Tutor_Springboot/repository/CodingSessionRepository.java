package com.ijse.AI_Coding_Tutor_Springboot.repository;

import com.ijse.AI_Coding_Tutor_Springboot.dto.GetSessionHistoryViewDTO;
import com.ijse.AI_Coding_Tutor_Springboot.dto.GetStatReportDTO;
import com.ijse.AI_Coding_Tutor_Springboot.dto.SessionHistoryDTO;
import com.ijse.AI_Coding_Tutor_Springboot.dto.StudentDTO;
import com.ijse.AI_Coding_Tutor_Springboot.entity.CodeAttempt;
import com.ijse.AI_Coding_Tutor_Springboot.entity.CodingSession;
import com.ijse.AI_Coding_Tutor_Springboot.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
            "GROUP BY cs.sessionId, cs.problem.problemText, cs.programmingLanguage, cs.startTime, r.ratingValue, cs.sessionStatus " +
            "ORDER BY cs.startTime DESC")
    List<SessionHistoryDTO> getSessionHistory(long userId);

//    @Query("SELECT new com.ijse.AI_Coding_Tutor_Springboot.dto.GetSessionHistoryViewDTO(" +
//            "cs.sessionId, cs.problem.problemText, cs.programmingLanguage.languageName, COUNT(h)) " +
//            "FROM CodingSession cs " +
//            "LEFT JOIN cs.hints h " +
//            "WHERE cs.sessionId = ?1 " +
//            "GROUP BY cs.sessionId, cs.problem.problemText, cs.programmingLanguage.languageName")
//    Optional<GetSessionHistoryViewDTO> getSessionHistoryView(long sessionId);

    @Query("SELECT new com.ijse.AI_Coding_Tutor_Springboot.dto.GetSessionHistoryViewDTO(" +
            "cs.sessionId, cs.problem.problemText, cs.programmingLanguage.languageName, COUNT(h), cs.sessionStatus) " +
            "FROM CodingSession cs " +
            "LEFT JOIN cs.hints h " +
            "WHERE cs.sessionId = ?1 " +
            "GROUP BY cs.sessionId, cs.problem.problemText, cs.programmingLanguage.languageName, cs.sessionStatus")
    Optional<GetSessionHistoryViewDTO> getSessionHistoryView(long sessionId);

    @Query("SELECT new com.ijse.AI_Coding_Tutor_Springboot.dto.GetStatReportDTO(COUNT(cs),COUNT(DISTINCT cs.student.studentId)) FROM CodingSession cs WHERE cs.startTime BETWEEN ?1 AND ?2")
    GetStatReportDTO getStatReportBetweenDates(LocalDateTime startDate, LocalDateTime endDate);

    @Query("SELECT cs.student FROM CodingSession cs WHERE cs.sessionId = ?1")
    Optional<Student> getStudentBySessionId(long sessionId);
}
