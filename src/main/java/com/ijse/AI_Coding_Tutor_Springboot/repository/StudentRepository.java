package com.ijse.AI_Coding_Tutor_Springboot.repository;

import com.ijse.AI_Coding_Tutor_Springboot.dto.GetStudentDetailsDTO;
import com.ijse.AI_Coding_Tutor_Springboot.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student,Long> {

    @Query("SELECT new com.ijse.AI_Coding_Tutor_Springboot.dto.GetStudentDetailsDTO(s.studentId,s.studentFullName,s.user.userName,s.studentContact,COUNT(s.codingSessions),COUNT(cs.rating),s.user.joinedDate,s.user.userStatus) FROM Student s JOIN s.codingSessions cs")
    List<GetStudentDetailsDTO> getStudentDetails();
}
