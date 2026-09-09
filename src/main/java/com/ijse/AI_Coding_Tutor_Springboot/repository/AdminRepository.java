package com.ijse.AI_Coding_Tutor_Springboot.repository;

import com.ijse.AI_Coding_Tutor_Springboot.dto.GetAdminDetailsDTO;
import com.ijse.AI_Coding_Tutor_Springboot.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin,Long> {

    @Query("SELECT a FROM Admin a WHERE a.user.userId = ?1")
    Optional<Admin> getAdminByUserId(long userId);

    @Query("SELECT new com.ijse.AI_Coding_Tutor_Springboot.dto.GetAdminDetailsDTO(a.adminFullName,a.user.userName,a.user.userStatus) FROM Admin a")
    List<GetAdminDetailsDTO> getAdminDetails();
}
