package com.ijse.AI_Coding_Tutor_Springboot.service;

import com.ijse.AI_Coding_Tutor_Springboot.entity.Admin;

import java.util.Optional;

public interface AdminService {

    public Admin getAdminNameByUserId(long userId);
}
