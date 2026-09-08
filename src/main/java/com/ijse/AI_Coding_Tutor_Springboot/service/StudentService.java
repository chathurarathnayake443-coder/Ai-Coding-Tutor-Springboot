package com.ijse.AI_Coding_Tutor_Springboot.service;

import com.ijse.AI_Coding_Tutor_Springboot.dto.*;
import com.ijse.AI_Coding_Tutor_Springboot.entity.Student;
import com.ijse.AI_Coding_Tutor_Springboot.entity.User;
import com.ijse.AI_Coding_Tutor_Springboot.enumerations.UserStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StudentService {

    public List<GetStudentDetailsDTO> getAllStudents();

    public String getStudentNameById(long userId);

    public long getCompletedSessionCount(long userId);

    public List<SessionHistoryDTO> getSessionHistory(long userId);

    public double getAverageRating(long userId);

    public StudentDTO getStudentById(long userId);

    public void updateStudentDetails(UpdateStudentDetailsDTO updateStudentDetailsDTO);
}
