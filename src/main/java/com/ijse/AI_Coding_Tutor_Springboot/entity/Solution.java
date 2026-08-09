package com.ijse.AI_Coding_Tutor_Springboot.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Solution {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long solutionId;
    private String solutionCode;
    private String explanation;
    private LocalDateTime generatedTime;

    @OneToOne
    @JoinColumn(name = "session_id")
    private CodingSession codingSession;
}
