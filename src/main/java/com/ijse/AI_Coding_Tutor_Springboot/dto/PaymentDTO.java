package com.ijse.AI_Coding_Tutor_Springboot.dto;

import com.ijse.AI_Coding_Tutor_Springboot.enumerations.PaymentStatus;
import com.ijse.AI_Coding_Tutor_Springboot.enumerations.SubscriptionPlanCategory;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class PaymentDTO {
    private long userId;
    private SubscriptionPlanCategory planCategory;
    private double paymentAmount;
}
