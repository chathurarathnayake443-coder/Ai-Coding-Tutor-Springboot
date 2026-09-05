package com.ijse.AI_Coding_Tutor_Springboot.service.impl;

import com.ijse.AI_Coding_Tutor_Springboot.entity.Admin;
import com.ijse.AI_Coding_Tutor_Springboot.repository.AdminRepository;
import com.ijse.AI_Coding_Tutor_Springboot.service.AdminService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;

    public AdminServiceImpl(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public Admin getAdminNameByUserId(long userId) {
        try{
            Optional<Admin> optionalAdmin = adminRepository.getAdminByUserId(userId);
            if(!optionalAdmin.isPresent()){
                throw new RuntimeException("Sorry, Admin Not Found");
            }
            Admin admin = optionalAdmin.get();
            return admin;
        }
        catch(Exception e) {
            throw e;
        }
    }
}
