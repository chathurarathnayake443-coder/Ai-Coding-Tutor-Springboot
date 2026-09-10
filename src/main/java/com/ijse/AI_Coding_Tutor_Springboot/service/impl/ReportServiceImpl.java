package com.ijse.AI_Coding_Tutor_Springboot.service.impl;

import com.ijse.AI_Coding_Tutor_Springboot.dto.*;
import com.ijse.AI_Coding_Tutor_Springboot.repository.CodingSessionRepository;
import com.ijse.AI_Coding_Tutor_Springboot.repository.RatingRepository;
import com.ijse.AI_Coding_Tutor_Springboot.repository.StudentRepository;
import com.ijse.AI_Coding_Tutor_Springboot.repository.UserRepository;
import com.ijse.AI_Coding_Tutor_Springboot.service.ReportService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {

    private final UserRepository userRepository;
    private final CodingSessionRepository codingSessionRepository;
    private final RatingRepository ratingRepository;
    private final StudentRepository studentRepository;

    public ReportServiceImpl(UserRepository userRepository, CodingSessionRepository codingSessionRepository, RatingRepository ratingRepository, StudentRepository studentRepository) {
        this.userRepository = userRepository;
        this.codingSessionRepository = codingSessionRepository;
        this.ratingRepository = ratingRepository;
        this.studentRepository = studentRepository;
    }

    public ReportDTO generateReport(LocalDateTime startDate, LocalDateTime endDate) {
        try{
            long totalStudentCount = userRepository.getTotalStudentCount();
            GetStatReportDTO getStatReportDTO = codingSessionRepository.getStatReportBetweenDates(startDate, endDate);
            List<RatingCountDTO> ratingList = ratingRepository.getStudentCountsByRatingValue();
            List<ReportStudentDTO> reportStudentDTOList = studentRepository.getStudentReportListBetweenDates(startDate, endDate);
            return new ReportDTO(totalStudentCount, getStatReportDTO, ratingList, reportStudentDTOList);
        }
        catch(Exception e){
            throw e;
        }
    }
}
