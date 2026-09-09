package com.ijse.AI_Coding_Tutor_Springboot.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class AdminDTO {
    private String adminFullName;
    private String adminEmail;
    private String adminPassword;
}
