package com.ijse.AI_Coding_Tutor_Springboot.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class CodeAttempt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long attemptId;
    private String code;
    private LocalDateTime submittedTime;
    private int attemptNumber;

    @ManyToOne
    @JoinColumn(name = "session_id")
    private CodingSession codingSession;

    @OneToMany(mappedBy = "codeAttempt",cascade = CascadeType.ALL)
    private List<CodeExecution> codeExecutions;
}
