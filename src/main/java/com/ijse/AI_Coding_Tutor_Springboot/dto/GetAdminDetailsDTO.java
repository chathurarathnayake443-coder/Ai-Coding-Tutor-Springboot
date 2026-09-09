package com.ijse.AI_Coding_Tutor_Springboot.dto;

import com.ijse.AI_Coding_Tutor_Springboot.enumerations.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class GetAdminDetailsDTO {
    private String adminName;
    private String adminEmail;
    private UserStatus adminStatus;
    private String adminContact;

    public GetAdminDetailsDTO(String adminName, String adminEmail, UserStatus adminStatus) {
        this.adminName = adminName;
        this.adminEmail = adminEmail;
        this.adminStatus = adminStatus;
    }
}
