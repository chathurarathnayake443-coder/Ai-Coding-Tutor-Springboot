package com.ijse.AI_Coding_Tutor_Springboot.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class GetSessionHistoryViewDTO {
    private long sessionId;
    private String codingProblem;
    private String programmingLanguage;
    private String lastExecutedCode;
    private int usedHintCount;
}
