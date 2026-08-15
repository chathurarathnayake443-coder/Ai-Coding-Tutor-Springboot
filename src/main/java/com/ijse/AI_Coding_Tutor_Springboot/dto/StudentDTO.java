package com.ijse.AI_Coding_Tutor_Springboot.dto;

import com.ijse.AI_Coding_Tutor_Springboot.entity.*;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class StudentDTO {
    private long studentId;
    private String studentFullName;
    private String studentEmail;
    private String studentContact;
    private String password;
}
