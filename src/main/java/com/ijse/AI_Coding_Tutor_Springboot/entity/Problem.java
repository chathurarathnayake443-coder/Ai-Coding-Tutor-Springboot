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

public class Problem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long problemId;
    private String problemText;
    private LocalDateTime createdTime;

    @OneToOne
    @JoinColumn(name = "session_id")
    private CodingSession codingSession;
}
