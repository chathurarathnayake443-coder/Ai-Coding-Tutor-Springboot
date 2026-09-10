package com.ijse.AI_Coding_Tutor_Springboot.repository;

import com.ijse.AI_Coding_Tutor_Springboot.entity.User;
import com.ijse.AI_Coding_Tutor_Springboot.enumerations.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserNameAndPassword(String userName, String userPassword);

    Optional<User> findByUserName(String userName);

    @Query("SELECT u.userStatus FROM User u WHERE u.userName = ?1")
    UserStatus getUserStatus(String userName);

    @Query("SELECT COUNT(s.studentId) FROM Student s")
    long getTotalStudentCount();
}
