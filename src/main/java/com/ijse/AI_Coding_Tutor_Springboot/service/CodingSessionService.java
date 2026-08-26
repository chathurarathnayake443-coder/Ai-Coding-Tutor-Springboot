package com.ijse.AI_Coding_Tutor_Springboot.service;

import com.ijse.AI_Coding_Tutor_Springboot.dto.CodingSessionDTO;
import com.ijse.AI_Coding_Tutor_Springboot.dto.EndSessionDTO;
import com.ijse.AI_Coding_Tutor_Springboot.dto.GetSessionHistoryViewDTO;
import com.ijse.AI_Coding_Tutor_Springboot.dto.ResponseCodingSessionDTO;
import com.ijse.AI_Coding_Tutor_Springboot.entity.*;
import com.ijse.AI_Coding_Tutor_Springboot.enumerations.CodingSessionStatus;

import java.time.LocalDateTime;
import java.util.Optional;

public interface CodingSessionService {

    public ResponseCodingSessionDTO createNewCodingSession(CodingSessionDTO codingSessionDTO);

    public void endSession(EndSessionDTO endSessionDTO);

    public GetSessionHistoryViewDTO getSessionHistoryView(long sessionId);
}
