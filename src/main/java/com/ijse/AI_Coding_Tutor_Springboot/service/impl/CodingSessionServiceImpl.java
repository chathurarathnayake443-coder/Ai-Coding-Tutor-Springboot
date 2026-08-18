package com.ijse.AI_Coding_Tutor_Springboot.service.impl;

import com.ijse.AI_Coding_Tutor_Springboot.dto.CodingSessionDTO;
import com.ijse.AI_Coding_Tutor_Springboot.dto.ResponseCodingSessionDTO;
import com.ijse.AI_Coding_Tutor_Springboot.entity.CodingSession;
import com.ijse.AI_Coding_Tutor_Springboot.entity.Problem;
import com.ijse.AI_Coding_Tutor_Springboot.entity.ProgrammingLanguage;
import com.ijse.AI_Coding_Tutor_Springboot.entity.Student;
import com.ijse.AI_Coding_Tutor_Springboot.enumerations.CodingSessionStatus;
import com.ijse.AI_Coding_Tutor_Springboot.repository.CodingSessionRepository;
import com.ijse.AI_Coding_Tutor_Springboot.repository.ProblemRepository;
import com.ijse.AI_Coding_Tutor_Springboot.repository.ProgrammingLanguageRepository;
import com.ijse.AI_Coding_Tutor_Springboot.repository.StudentRepository;
import com.ijse.AI_Coding_Tutor_Springboot.service.CodingSessionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CodingSessionServiceImpl implements CodingSessionService {

    private final CodingSessionRepository codingSessionRepository;
    private final ProblemRepository problemRepository;
    private final StudentRepository studentRepository;
    private final ProgrammingLanguageRepository programmingLanguageRepository;

    public CodingSessionServiceImpl(CodingSessionRepository codingSessionRepository, ProblemRepository problemRepository, StudentRepository studentRepository, ProgrammingLanguageRepository programmingLanguageRepository) {
        this.codingSessionRepository = codingSessionRepository;
        this.problemRepository = problemRepository;
        this.studentRepository = studentRepository;
        this.programmingLanguageRepository = programmingLanguageRepository;
    }

    @Transactional
    public ResponseCodingSessionDTO createNewCodingSession(CodingSessionDTO codingSessionDTO){
        try{
            CodingSession codingSession = new CodingSession();

            codingSession.setStartTime(LocalDateTime.now());
            codingSession.setSessionStatus(CodingSessionStatus.IN_PROGRESS);

            Optional<Student> optionalStudent = studentRepository.getStudentByUserId(codingSessionDTO.getStudentId());
            if(!optionalStudent.isPresent()){
                throw new RuntimeException("Sorry, Student Not Found!");
            }

            Student student = optionalStudent.get();
            System.out.println("Service Codeeee");
            System.out.println(student.getStudentFullName());
            codingSession.setStudent(student);

            Optional<ProgrammingLanguage> optionalProgrammingLanguage = programmingLanguageRepository.findByLanguageNameIgnoreCase(codingSessionDTO.getProgrammingLanguage());
            if(!optionalProgrammingLanguage.isPresent()){
                throw new RuntimeException("Sorry, Programming Language Not Found!");
            }
            ProgrammingLanguage programmingLanguage = optionalProgrammingLanguage.get();
            System.out.println(programmingLanguage.getLanguageName());
            codingSession.setProgrammingLanguage(programmingLanguage);

            Problem problem = new Problem();
            problem.setProblemText(codingSessionDTO.getCodingProblem());
            problem.setCreatedTime(LocalDateTime.now());
            problem.setCodingSession(codingSession);
            System.out.println(codingSessionDTO.getCodingProblem());

            codingSession.setProblem(problem);
            CodingSession newCodingSession = codingSessionRepository.save(codingSession);
            return new ResponseCodingSessionDTO(newCodingSession.getSessionId(),newCodingSession.getStartTime(),newCodingSession.getEndTime(),newCodingSession.getSessionStatus(),newCodingSession.getProgrammingLanguage().getLanguageId(),newCodingSession.getStudent().getStudentId());
        }
        catch(Exception e){
            throw e;
        }
    }
}
