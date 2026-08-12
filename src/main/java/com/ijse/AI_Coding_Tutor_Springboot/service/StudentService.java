package com.ijse.AI_Coding_Tutor_Springboot.service;

import com.ijse.AI_Coding_Tutor_Springboot.dto.GetStudentDetailsDTO;

import java.util.List;

public interface StudentService {

    public List<GetStudentDetailsDTO> getAllStudents();
}
