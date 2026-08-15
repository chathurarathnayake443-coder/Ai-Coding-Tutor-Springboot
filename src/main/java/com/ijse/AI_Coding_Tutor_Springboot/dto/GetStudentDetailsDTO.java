package com.ijse.AI_Coding_Tutor_Springboot.dto;

import com.ijse.AI_Coding_Tutor_Springboot.entity.Rating;
import com.ijse.AI_Coding_Tutor_Springboot.enumerations.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class GetStudentDetailsDTO {
    private long studentId;
    private String studentName;
    private String studentEmail;
    private String studentPhone;
    private Long sessionCount;
    private Double avgRating;
    private LocalDateTime joinedDate;
    private UserStatus studentStatus;
}
