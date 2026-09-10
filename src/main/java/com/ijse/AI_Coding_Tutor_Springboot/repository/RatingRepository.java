package com.ijse.AI_Coding_Tutor_Springboot.repository;

import com.ijse.AI_Coding_Tutor_Springboot.dto.RatingCountDTO;
import com.ijse.AI_Coding_Tutor_Springboot.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating,Long> {

    @Query("SELECT AVG(r.ratingValue) FROM Rating r WHERE r.codingSession.student.studentId = ?1")
    Double findAvgRatingByStudentId(long studentId);

    @Query("SELECT new com.ijse.AI_Coding_Tutor_Springboot.dto.RatingCountDTO(r.ratingValue, COUNT(DISTINCT r.codingSession.student.studentId)) " +
            "FROM Rating r GROUP BY r.ratingValue ORDER BY r.ratingValue")
    List<RatingCountDTO> getStudentCountsByRatingValue();
}
