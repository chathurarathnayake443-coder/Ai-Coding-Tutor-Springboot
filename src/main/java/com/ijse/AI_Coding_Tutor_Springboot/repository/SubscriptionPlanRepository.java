package com.ijse.AI_Coding_Tutor_Springboot.repository;

import com.ijse.AI_Coding_Tutor_Springboot.entity.SubscriptionPlan;
import com.ijse.AI_Coding_Tutor_Springboot.enumerations.SubscriptionPlanCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubscriptionPlanRepository extends JpaRepository<SubscriptionPlan,Long> {

    @Query("SELECT s FROM SubscriptionPlan s WHERE s.subscriptionPlanCategory = 'FREE'")
    Optional<SubscriptionPlan> getFreeSubscriptionPlanId();

    @Query("SELECT sp FROM SubscriptionPlan sp WHERE sp.subscriptionPlanCategory = ?1")
    Optional<SubscriptionPlan> getSubscriptionPlanByCategory(SubscriptionPlanCategory category);
}
