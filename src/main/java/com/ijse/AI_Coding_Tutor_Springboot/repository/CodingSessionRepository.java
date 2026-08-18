package com.ijse.AI_Coding_Tutor_Springboot.repository;

import com.ijse.AI_Coding_Tutor_Springboot.entity.CodingSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CodingSessionRepository extends JpaRepository<CodingSession, Long> {
}
