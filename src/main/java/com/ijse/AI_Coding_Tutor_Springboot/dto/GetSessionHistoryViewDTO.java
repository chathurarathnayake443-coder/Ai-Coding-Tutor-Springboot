package com.ijse.AI_Coding_Tutor_Springboot.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class GetSessionHistoryViewDTO {
    private long sessionId;
    private String codingProblem;
    private String programmingLanguage;
    private String lastExecutedCode;
    private long usedHintCount;
    private List<String> hintTextList;

    public GetSessionHistoryViewDTO(long sessionId, String codingProblem, String programmingLanguage, long usedHintCount) {
        this.sessionId = sessionId;
        this.codingProblem = codingProblem;
        this.programmingLanguage = programmingLanguage;
        this.usedHintCount = usedHintCount;
    }
}
