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

public class SessionHistoryDTO {
    private long sessionId;
    private String codingProblem;
    private String programmingLanguage;
    private long usedHintCount;
    private LocalDateTime startDate;
    private Integer ratingValue;
    private CodingSessionStatus sessionStatus;
}
