package com.ijse.AI_Coding_Tutor_Springboot.dto;

import com.ijse.AI_Coding_Tutor_Springboot.enumerations.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class AuthDTO {
    private String userName;
    private String password;
    private String userRoles;
    private UserStatus userStatus;

    public AuthDTO(String userName, String password, String userRoles) {
        this.userName = userName;
        this.password = password;
        this.userRoles = userRoles;
    }
}
