package com.ijse.AI_Coding_Tutor_Springboot.service;

import com.ijse.AI_Coding_Tutor_Springboot.dto.PaymentDTO;
import com.ijse.AI_Coding_Tutor_Springboot.entity.Payment;
import com.ijse.AI_Coding_Tutor_Springboot.entity.Student;
import com.ijse.AI_Coding_Tutor_Springboot.entity.SubscriptionPlan;
import com.ijse.AI_Coding_Tutor_Springboot.enumerations.PaymentStatus;
import com.ijse.AI_Coding_Tutor_Springboot.exceptions.CustomException;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PaymentService {

    public void processPayment(PaymentDTO paymentDTO);
}
