package com.ijse.AI_Coding_Tutor_Springboot.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class HintRequestDTO {
    private long sessionId;
    private int hintNumber;
    private String language;
    private String codingProblem;
    private String studentCode;
}
