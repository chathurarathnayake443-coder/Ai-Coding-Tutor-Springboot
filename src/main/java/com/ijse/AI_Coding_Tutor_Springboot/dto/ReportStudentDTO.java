package com.ijse.AI_Coding_Tutor_Springboot.dto;

import com.ijse.AI_Coding_Tutor_Springboot.enumerations.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class ReportStudentDTO {
    private String studentName;
    private String studentEmail;
    private long sessionCount;
    private double averageRating;
    private LocalDateTime startTime;
    private UserStatus studentStatus;
}
