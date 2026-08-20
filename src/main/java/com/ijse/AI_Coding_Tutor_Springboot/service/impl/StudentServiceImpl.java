package com.ijse.AI_Coding_Tutor_Springboot.service.impl;

import com.ijse.AI_Coding_Tutor_Springboot.dto.GetStudentDetailsDTO;
import com.ijse.AI_Coding_Tutor_Springboot.dto.StudentDTO;
import com.ijse.AI_Coding_Tutor_Springboot.entity.Student;
import com.ijse.AI_Coding_Tutor_Springboot.repository.StudentRepository;
import com.ijse.AI_Coding_Tutor_Springboot.service.StudentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    private StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
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

}
