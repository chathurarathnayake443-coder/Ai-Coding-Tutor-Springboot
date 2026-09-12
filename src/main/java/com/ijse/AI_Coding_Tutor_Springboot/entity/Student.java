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

public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long studentId;
    private String studentFullName;
    private String studentContact;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "subscription_id")
    private UserSubscription userSubscription;

    @OneToMany(mappedBy = "student")
    private List<CodingSession> codingSessions;

    @OneToOne(mappedBy = "student",cascade = CascadeType.ALL)
    private AnalyticRecord analyticRecord;

    @OneToMany(mappedBy = "student")
    private List<Payment> paymentList;
}
