package com.ijse.AI_Coding_Tutor_Springboot.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class UpdateAdminDetailsDTO {
    private long userId;
    private String adminName;
    private String adminContact;
    private String oldPassword;
    private String newPassword;
}
