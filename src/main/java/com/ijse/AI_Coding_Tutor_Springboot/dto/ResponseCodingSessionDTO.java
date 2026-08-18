package com.ijse.AI_Coding_Tutor_Springboot.dto;

import com.ijse.AI_Coding_Tutor_Springboot.enumerations.CodingSessionStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class ResponseCodingSessionDTO {
    private long sessionId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private CodingSessionStatus sessionStatus;
    private long languageId;
    private long studentId;
}
