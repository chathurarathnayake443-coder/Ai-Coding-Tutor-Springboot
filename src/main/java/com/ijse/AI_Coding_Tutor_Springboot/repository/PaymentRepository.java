package com.ijse.AI_Coding_Tutor_Springboot.repository;

import com.ijse.AI_Coding_Tutor_Springboot.entity.Payment;
import com.ijse.AI_Coding_Tutor_Springboot.entity.SubscriptionPlan;
import com.ijse.AI_Coding_Tutor_Springboot.enumerations.SubscriptionPlanCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
