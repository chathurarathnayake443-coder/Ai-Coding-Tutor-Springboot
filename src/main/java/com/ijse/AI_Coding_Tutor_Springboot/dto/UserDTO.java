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

public class UserDTO {
    private long userId;
    private String userName;
    private String password;
    private UserStatus userStatus;
    private LocalDateTime joinedDate;
    private String userRoles;
}
