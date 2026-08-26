package com.ijse.AI_Coding_Tutor_Springboot.service.impl;

import com.ijse.AI_Coding_Tutor_Springboot.dto.GetStudentDetailsDTO;
import com.ijse.AI_Coding_Tutor_Springboot.dto.SessionHistoryDTO;
import com.ijse.AI_Coding_Tutor_Springboot.dto.StudentDTO;
import com.ijse.AI_Coding_Tutor_Springboot.entity.Student;
import com.ijse.AI_Coding_Tutor_Springboot.repository.CodingSessionRepository;
import com.ijse.AI_Coding_Tutor_Springboot.repository.StudentRepository;
import com.ijse.AI_Coding_Tutor_Springboot.service.StudentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    private StudentRepository studentRepository;
    private CodingSessionRepository codingSessionRepository;

    public StudentServiceImpl(StudentRepository studentRepository, CodingSessionRepository codingSessionRepository) {
        this.studentRepository = studentRepository;
        this.codingSessionRepository = codingSessionRepository;
    }

    public List<GetStudentDetailsDTO> getAllStudents(){
        try{
            return studentRepository.getStudentDetails();
        }
        catch(Exception e){
            throw e;
        }
    }

    public String getStudentNameById(long userId){
        Optional<Student> studentOptional = studentRepository.getStudentByUserId(userId);
        if(!studentOptional.isPresent()){
            throw new RuntimeException("Sorry Student not found");
        }
        Student student = studentOptional.get();
        String studentName = student.getStudentFullName();
        return studentName;
    }

    public long getCompletedSessionCount(long userId){
        try{
            System.out.println(userId);
            Optional<Student> studentOptional = studentRepository.getStudentByUserId(userId);
            if(!studentOptional.isPresent()){
                throw new RuntimeException("Sorry Student not found");
            }
            Student student = studentOptional.get();
            System.out.println(student.getStudentId());
            long sessionCount = codingSessionRepository.getCompletedSessionCount(student.getStudentId());
            System.out.println(sessionCount);
            return sessionCount;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return 0;
    }


    public List<SessionHistoryDTO> getSessionHistory(long userId){
        try{
            Optional<Student> studentOptional = studentRepository.getStudentByUserId(userId);
            if(!studentOptional.isPresent()){
                throw new RuntimeException("Sorry Student not found");
            }
            Student student = studentOptional.get();

            List<SessionHistoryDTO> sessionHistoryDTOList = codingSessionRepository.getSessionHistory(student.getStudentId());
            for(SessionHistoryDTO sessionHistoryDTO : sessionHistoryDTOList){
                System.out.println(sessionHistoryDTO.getSessionId());
            }
            return sessionHistoryDTOList;

        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
