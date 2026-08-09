package com.ijse.AI_Coding_Tutor_Springboot.entity;

import com.ijse.AI_Coding_Tutor_Springboot.enumerations.PlanStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class SubscriptionPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long planId;
    private String planName;
    private String price;
    private String description;
    private int maxHintCount;
    @Enumerated(EnumType.STRING)
    private PlanStatus planStatus;

    @OneToMany(mappedBy = "subscriptionPlan")
    private List<UserSubscription> userSubscriptions;

    @OneToMany(mappedBy = "subscriptionPlan")
    private List<Payment> payments;
}
