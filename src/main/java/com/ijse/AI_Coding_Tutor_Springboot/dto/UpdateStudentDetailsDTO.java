package com.ijse.AI_Coding_Tutor_Springboot.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class UpdateStudentDetailsDTO {
    private long userId;
    private String newStudentName;
    private String newPhoneNumber;
    private String oldPassword;
    private String newPassword;
}
