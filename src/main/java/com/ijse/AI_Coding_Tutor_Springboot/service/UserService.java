package com.ijse.AI_Coding_Tutor_Springboot.service;

import com.ijse.AI_Coding_Tutor_Springboot.dto.UserDTO;
import com.ijse.AI_Coding_Tutor_Springboot.entity.User;

import java.util.Optional;

public interface UserService {

    public UserDTO getUserDetails(String userName, String password, String userRole);
}
