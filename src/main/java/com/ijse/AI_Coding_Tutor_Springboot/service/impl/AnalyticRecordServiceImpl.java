package com.ijse.AI_Coding_Tutor_Springboot.service.impl;

import com.ijse.AI_Coding_Tutor_Springboot.dto.AnalyticRecordDTO;
import com.ijse.AI_Coding_Tutor_Springboot.entity.AnalyticRecord;
import com.ijse.AI_Coding_Tutor_Springboot.entity.Student;
import com.ijse.AI_Coding_Tutor_Springboot.exceptions.CustomException;
import com.ijse.AI_Coding_Tutor_Springboot.repository.AnalyticRecordRepository;
import com.ijse.AI_Coding_Tutor_Springboot.repository.StudentRepository;
import com.ijse.AI_Coding_Tutor_Springboot.service.AnalyticRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class AnalyticRecordServiceImpl implements AnalyticRecordService {

    private final AnalyticRecordRepository analyticRecordRepository;
    private final StudentRepository studentRepository;

    public AnalyticRecordServiceImpl(AnalyticRecordRepository analyticRecordRepository, StudentRepository studentRepository) {
        this.analyticRecordRepository = analyticRecordRepository;
        this.studentRepository = studentRepository;
    }

    public AnalyticRecordDTO getAnalyticRecordForStudent(long userId){
        try{
            Optional<Student> optionalStudent = studentRepository.getStudentByUserId(userId);
            if(!optionalStudent.isPresent()){
                throw new CustomException(404,"Sorry Student Not Found");
            }
            Student student = optionalStudent.get();

            Optional<AnalyticRecord> optionalAnalyticRecord = analyticRecordRepository.findAnalyticRecordByStudentId(student.getStudentId());
            if(!optionalAnalyticRecord.isPresent()){
                throw new CustomException(404,"Sorry Analytic Record Not Found");
            }
            AnalyticRecord analyticRecord = optionalAnalyticRecord.get();
            return new AnalyticRecordDTO(analyticRecord.getTotalAttemptCount(),analyticRecord.getTotalExecutionCount(),analyticRecord.getTotalHintCount());
        }
        catch(Exception e){
            throw e;
        }
    }
}
