package com.ijse.AI_Coding_Tutor_Springboot.repository;

import com.ijse.AI_Coding_Tutor_Springboot.entity.UserSubscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserSubscriptionRepository extends JpaRepository<UserSubscription, Long> {
}
