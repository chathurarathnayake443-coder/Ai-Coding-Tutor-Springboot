package com.ijse.AI_Coding_Tutor_Springboot.service;

import com.ijse.AI_Coding_Tutor_Springboot.dto.CodingSessionDTO;
import com.ijse.AI_Coding_Tutor_Springboot.dto.ResponseCodingSessionDTO;
import com.ijse.AI_Coding_Tutor_Springboot.entity.CodingSession;
import com.ijse.AI_Coding_Tutor_Springboot.entity.Problem;
import com.ijse.AI_Coding_Tutor_Springboot.entity.ProgrammingLanguage;
import com.ijse.AI_Coding_Tutor_Springboot.entity.Student;
import com.ijse.AI_Coding_Tutor_Springboot.enumerations.CodingSessionStatus;

import java.time.LocalDateTime;
import java.util.Optional;

public interface CodingSessionService {

    public ResponseCodingSessionDTO createNewCodingSession(CodingSessionDTO codingSessionDTO);
}
