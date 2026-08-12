package com.ijse.AI_Coding_Tutor_Springboot.dto;

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
}
