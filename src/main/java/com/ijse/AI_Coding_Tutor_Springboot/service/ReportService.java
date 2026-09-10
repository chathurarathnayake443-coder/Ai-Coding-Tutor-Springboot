package com.ijse.AI_Coding_Tutor_Springboot.service;

import com.ijse.AI_Coding_Tutor_Springboot.dto.*;

import java.time.LocalDateTime;
import java.util.List;

public interface ReportService {

    public ReportDTO generateReport(LocalDateTime startDate, LocalDateTime endDate);
}
