package com.ijse.AI_Coding_Tutor_Springboot.entity;

import com.ijse.AI_Coding_Tutor_Springboot.enumerations.CodingSessionStatus;
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

public class CodingSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long sessionId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    @Enumerated(EnumType.STRING)
    private CodingSessionStatus sessionStatus;

    @ManyToOne
    @JoinColumn(name = "language_id")
    private ProgrammingLanguage programmingLanguage;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @OneToOne(mappedBy = "codingSession", cascade = CascadeType.ALL)
    private Problem problem;

    @OneToMany(mappedBy = "codingSession")
    private List<CodeAttempt> codeAttempts;

    @OneToOne(mappedBy = "codingSession")
    private Solution solution;

    @OneToMany(mappedBy = "codingSession")
    private List<Hint> hints;

    @OneToOne(mappedBy = "codingSession")
    private Rating rating;
}
