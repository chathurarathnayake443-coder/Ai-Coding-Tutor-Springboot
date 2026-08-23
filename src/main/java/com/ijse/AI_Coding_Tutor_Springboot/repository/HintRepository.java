package com.ijse.AI_Coding_Tutor_Springboot.repository;

import com.ijse.AI_Coding_Tutor_Springboot.entity.Hint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HintRepository extends JpaRepository<Hint, Long> {
}
