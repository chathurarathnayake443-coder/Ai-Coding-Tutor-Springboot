package com.ijse.AI_Coding_Tutor_Springboot.service;

import com.ijse.AI_Coding_Tutor_Springboot.dto.StudentDTO;
import com.ijse.AI_Coding_Tutor_Springboot.dto.UserDTO;
import com.ijse.AI_Coding_Tutor_Springboot.entity.Student;
import com.ijse.AI_Coding_Tutor_Springboot.entity.User;
import com.ijse.AI_Coding_Tutor_Springboot.enumerations.UserStatus;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public interface UserService {

    public UserDTO getUserDetails(String userName, String password, String userRole);

    public void signupUserStudent(StudentDTO studentDTO);

    public UserDTO findOrCreateByGoogleEmail(String email, String name);
}
