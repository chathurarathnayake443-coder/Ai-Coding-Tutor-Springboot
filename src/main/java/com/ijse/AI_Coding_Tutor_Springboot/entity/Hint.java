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

public class Hint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long hintId;
    private String hintText;
    private int hintNumber;
    private LocalDateTime createdTime;

    @ManyToOne
    @JoinColumn(name = "session_id")
    private CodingSession codingSession;
}
