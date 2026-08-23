package com.ijse.AI_Coding_Tutor_Springboot.service;

import com.ijse.AI_Coding_Tutor_Springboot.dto.GetStudentDetailsDTO;
import com.ijse.AI_Coding_Tutor_Springboot.dto.StudentDTO;
import com.ijse.AI_Coding_Tutor_Springboot.entity.Student;

import java.util.List;
import java.util.Optional;

public interface StudentService {

    public List<GetStudentDetailsDTO> getAllStudents();

    public String getStudentNameById(long userId);

    public long getCompletedSessionCount(long userId);
}
