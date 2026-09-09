package com.ijse.AI_Coding_Tutor_Springboot.service;

import com.ijse.AI_Coding_Tutor_Springboot.dto.AdminDTO;
import com.ijse.AI_Coding_Tutor_Springboot.dto.GetAdminDetailsDTO;
import com.ijse.AI_Coding_Tutor_Springboot.entity.Admin;
import com.ijse.AI_Coding_Tutor_Springboot.entity.User;
import com.ijse.AI_Coding_Tutor_Springboot.enumerations.UserStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AdminService {

    public Admin getAdminNameByUserId(long userId);

    public void saveAdmin(AdminDTO adminDTO);

    public List<GetAdminDetailsDTO> getAdminDetails();

    public GetAdminDetailsDTO getAdminDetailById(long userId);
}
