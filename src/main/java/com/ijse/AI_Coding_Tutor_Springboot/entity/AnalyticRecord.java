package com.ijse.AI_Coding_Tutor_Springboot.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class AnalyticRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long analyticId;
    private long totalAttemptCount;
    private long totalHintCount;
    private long totalExecutionCount;

    @OneToOne
    @JoinColumn(name = "student_id")
    private Student student;
}
