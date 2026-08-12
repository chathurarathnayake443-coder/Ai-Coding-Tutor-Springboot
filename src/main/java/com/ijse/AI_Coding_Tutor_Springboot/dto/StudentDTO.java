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
    private String studentContact;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "subscription_id")
    private UserSubscription userSubscription;

    @OneToMany(mappedBy = "student")
    private List<CodingSession> codingSessions;

    @OneToMany(mappedBy = "student")
    private List<Rating> ratings;

    @OneToOne(mappedBy = "student")
    private AnalyticRecord analyticRecord;

    @OneToOne(mappedBy = "student")
    private Payment payment;
}
