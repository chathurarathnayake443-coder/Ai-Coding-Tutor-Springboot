package com.ijse.AI_Coding_Tutor_Springboot.repository;

import com.ijse.AI_Coding_Tutor_Springboot.dto.GetStudentDetailsDTO;
import com.ijse.AI_Coding_Tutor_Springboot.dto.GetUserPasswordAndEmailDTO;
import com.ijse.AI_Coding_Tutor_Springboot.dto.ReportStudentDTO;
import com.ijse.AI_Coding_Tutor_Springboot.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {

    @Query("""
    SELECT new com.ijse.AI_Coding_Tutor_Springboot.dto.GetStudentDetailsDTO(
        s.studentId,
        s.studentFullName,
        s.user.userName,
        s.studentContact,
        COUNT(DISTINCT cs.sessionId),
        COALESCE(AVG(r.ratingValue), 0.0),
        s.user.joinedDate,
        s.user.userStatus
    )
    FROM Student s
    LEFT JOIN s.codingSessions cs
    LEFT JOIN cs.rating r
    GROUP BY s.studentId, s.studentFullName, s.user.userName, s.studentContact, s.user.joinedDate, s.user.userStatus
    """)
    List<GetStudentDetailsDTO> getStudentDetails();

    @Query("SELECT s FROM Student s WHERE s.user.userId = ?1")
    Optional<Student> getStudentByUserId(long userId);

    @Query("SELECT new com.ijse.AI_Coding_Tutor_Springboot.dto.GetUserPasswordAndEmailDTO(s.user.userName,s.user.password) FROM Student s WHERE s.studentId = ?1")
    Optional<GetUserPasswordAndEmailDTO> getUserPasswordAndEmail(long studentId);

    @Query("""
    SELECT new com.ijse.AI_Coding_Tutor_Springboot.dto.GetStudentDetailsDTO(
        s.studentId,
        s.studentFullName,
        s.user.userName,
        s.studentContact,
        COUNT(DISTINCT cs.sessionId),
        COALESCE(AVG(r.ratingValue), 0.0),
        s.user.joinedDate,
        s.user.userStatus
    )
    FROM Student s
    LEFT JOIN s.codingSessions cs
    LEFT JOIN cs.rating r
    WHERE (?1 IS NULL OR s.studentFullName LIKE %?1%)
    GROUP BY s.studentId, s.studentFullName, s.user.userName, s.studentContact, s.user.joinedDate, s.user.userStatus
    """)
    List<GetStudentDetailsDTO> searchFilterStudents(String studentName);

    @Query("""
            SELECT new com.ijse.AI_Coding_Tutor_Springboot.dto.ReportStudentDTO(
                s.studentFullName,
                s.user.userName,
                COUNT(DISTINCT cs.sessionId),
                COALESCE(AVG(r.ratingValue), 0.0),
                MAX(cs.startTime),
                s.user.userStatus
            )
            FROM Student s
            LEFT JOIN s.codingSessions cs
            LEFT JOIN cs.rating r
            WHERE cs.startTime BETWEEN ?1 AND ?2
            GROUP BY s.studentId, s.studentFullName, s.user.userName, s.user.userStatus
            """)
    List<ReportStudentDTO> getStudentReportListBetweenDates(LocalDateTime startDate, LocalDateTime endDate);

}
