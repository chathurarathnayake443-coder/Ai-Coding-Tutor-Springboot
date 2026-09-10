package com.ijse.AI_Coding_Tutor_Springboot.service.impl;

import com.ijse.AI_Coding_Tutor_Springboot.dto.CodingSessionDTO;
import com.ijse.AI_Coding_Tutor_Springboot.dto.EndSessionDTO;
import com.ijse.AI_Coding_Tutor_Springboot.dto.GetSessionHistoryViewDTO;
import com.ijse.AI_Coding_Tutor_Springboot.dto.ResponseCodingSessionDTO;
import com.ijse.AI_Coding_Tutor_Springboot.entity.*;
import com.ijse.AI_Coding_Tutor_Springboot.enumerations.CodingSessionStatus;
import com.ijse.AI_Coding_Tutor_Springboot.exceptions.CustomException;
import com.ijse.AI_Coding_Tutor_Springboot.repository.*;
import com.ijse.AI_Coding_Tutor_Springboot.service.CodingSessionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CodingSessionServiceImpl implements CodingSessionService {

    private final CodingSessionRepository codingSessionRepository;
    private final ProblemRepository problemRepository;
    private final StudentRepository studentRepository;
    private final ProgrammingLanguageRepository programmingLanguageRepository;
    private final RatingRepository ratingRepository;
    private final CodeAttemptRepository codeAttemptRepository;
    private final HintRepository hintRepository;

    public CodingSessionServiceImpl(CodingSessionRepository codingSessionRepository, ProblemRepository problemRepository, StudentRepository studentRepository, ProgrammingLanguageRepository programmingLanguageRepository, RatingRepository ratingRepository, CodeAttemptRepository codeAttemptRepository, HintRepository hintRepository) {
        this.codingSessionRepository = codingSessionRepository;
        this.problemRepository = problemRepository;
        this.studentRepository = studentRepository;
        this.programmingLanguageRepository = programmingLanguageRepository;
        this.ratingRepository = ratingRepository;
        this.codeAttemptRepository = codeAttemptRepository;
        this.hintRepository = hintRepository;
    }

    @Transactional
    public ResponseCodingSessionDTO createNewCodingSession(CodingSessionDTO codingSessionDTO){
            CodingSession codingSession = new CodingSession();

            codingSession.setStartTime(LocalDateTime.now());
            codingSession.setSessionStatus(CodingSessionStatus.IN_PROGRESS);

            Optional<Student> optionalStudent = studentRepository.getStudentByUserId(codingSessionDTO.getStudentId());
            if(!optionalStudent.isPresent()){
                throw new CustomException(404,"Sorry, Student Not Found!");
            }

            Student student = optionalStudent.get();
            System.out.println("Service Codeeee");
            System.out.println(student.getStudentFullName());
            codingSession.setStudent(student);

            Optional<ProgrammingLanguage> optionalProgrammingLanguage = programmingLanguageRepository.findByLanguageNameIgnoreCase(codingSessionDTO.getProgrammingLanguage());
            if(!optionalProgrammingLanguage.isPresent()){
                throw new CustomException(404,"Sorry, Programming Language Not Found!");
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

    public void endSession(EndSessionDTO endSessionDTO){
            Optional<CodingSession> optionalCodingSession = codingSessionRepository.findById(endSessionDTO.getSessionId());
            if(!optionalCodingSession.isPresent()){
                throw new CustomException(404,"Sorry, Session Not Found!");
            }
            CodingSession codingSession = optionalCodingSession.get();
            codingSession.setEndTime(LocalDateTime.now());
            codingSession.setSessionStatus(CodingSessionStatus.COMPLETED);
            codingSessionRepository.save(codingSession);

            Rating rating = new Rating();
            rating.setSubmittedTime(LocalDateTime.now());
            rating.setCodingSession(codingSession);
            rating.setRatingValue(endSessionDTO.getRatingValue());
            rating.setFeedbackText(endSessionDTO.getRatingFeedback());
            ratingRepository.save(rating);
    }

    public GetSessionHistoryViewDTO getSessionHistoryView(long sessionId){
            Optional<GetSessionHistoryViewDTO> optionalSessionView = codingSessionRepository.getSessionHistoryView(sessionId);
            if(!optionalSessionView.isPresent()){
                throw new CustomException(404,"Sorry, Session Not Found!");
            }
            GetSessionHistoryViewDTO sessionView = optionalSessionView.get();

            Optional<CodeAttempt> lastExecutedCode = codeAttemptRepository.findTopByCodingSession_SessionIdOrderByAttemptNumberDesc(sessionId);
            String lastCode = "";
            if(!lastExecutedCode.isPresent()){
                lastCode = "";
            }
            else{
                CodeAttempt codeAttempt = lastExecutedCode.get();
                lastCode = codeAttempt.getCode();
            }

            List<String> hintList = hintRepository.getHintListForSession(sessionId);
            sessionView.setHintTextList(hintList);

            sessionView.setLastExecutedCode(lastCode);

            return sessionView;
    }
}
