package com.ijse.AI_Coding_Tutor_Springboot.entity;

import com.ijse.AI_Coding_Tutor_Springboot.enumerations.ExecutionStatus;
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

public class CodeExecution {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long executionId;
    private String output;
    private String errorMessage;
    private LocalDateTime executedTime;
    @Enumerated(EnumType.STRING)
    private ExecutionStatus executionStatus;

    @ManyToOne
    @JoinColumn(name = "attempt_id")
    private CodeAttempt codeAttempt;
}
