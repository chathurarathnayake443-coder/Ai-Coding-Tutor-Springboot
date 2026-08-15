package com.ijse.AI_Coding_Tutor_Springboot.repository;

import com.ijse.AI_Coding_Tutor_Springboot.dto.GetStudentDetailsDTO;
import com.ijse.AI_Coding_Tutor_Springboot.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {

    @Query("""
    SELECT new com.ijse.AI_Coding_Tutor_Springboot.dto.GetStudentDetailsDTO(
        s.studentId,
        s.studentFullName,
        s.user.userName,
        s.studentContact,
        COUNT(DISTINCT cs.sessionId),
        AVG(r.ratingValue),
        s.user.joinedDate,
        s.user.userStatus
    )
    FROM Student s
    LEFT JOIN s.codingSessions cs
    LEFT JOIN cs.rating r
    GROUP BY s.studentId, s.studentFullName, s.user.userName, s.studentContact, s.user.joinedDate, s.user.userStatus
    """)
    List<GetStudentDetailsDTO> getStudentDetails();
}
