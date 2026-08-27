package com.ijse.AI_Coding_Tutor_Springboot.repository;

import com.ijse.AI_Coding_Tutor_Springboot.entity.Hint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HintRepository extends JpaRepository<Hint, Long> {

    @Query("SELECT h.hintText FROM Hint h WHERE h.codingSession.sessionId = ?1 ORDER BY h.hintNumber")
    List<String> getHintListForSession(long sessionId);
}
