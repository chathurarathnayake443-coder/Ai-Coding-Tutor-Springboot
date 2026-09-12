package com.ijse.AI_Coding_Tutor_Springboot.service;

import com.ijse.AI_Coding_Tutor_Springboot.dto.AnalyticRecordDTO;
import com.ijse.AI_Coding_Tutor_Springboot.entity.AnalyticRecord;
import com.ijse.AI_Coding_Tutor_Springboot.entity.Student;
import com.ijse.AI_Coding_Tutor_Springboot.exceptions.CustomException;

import java.util.Optional;

public interface AnalyticRecordService {

    public AnalyticRecordDTO getAnalyticRecordForStudent(long userId);
}
