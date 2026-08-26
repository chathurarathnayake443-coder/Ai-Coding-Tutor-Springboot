package com.ijse.AI_Coding_Tutor_Springboot.repository;

import com.ijse.AI_Coding_Tutor_Springboot.entity.CodeAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CodeAttemptRepository extends JpaRepository<CodeAttempt,Long> {

    @Query("SELECT MAX(c.attemptNumber) FROM CodeAttempt c WHERE c.codingSession.sessionId = ?1")
    Integer getAttemptNumberBySessionId(long sessionId);

    Optional<CodeAttempt> findTopByCodingSession_SessionIdOrderByAttemptNumberDesc(long sessionId);
}
