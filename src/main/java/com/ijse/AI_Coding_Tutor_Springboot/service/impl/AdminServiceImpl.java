package com.ijse.AI_Coding_Tutor_Springboot.service.impl;

import com.ijse.AI_Coding_Tutor_Springboot.dto.AdminDTO;
import com.ijse.AI_Coding_Tutor_Springboot.dto.GetAdminDetailsDTO;
import com.ijse.AI_Coding_Tutor_Springboot.entity.Admin;
import com.ijse.AI_Coding_Tutor_Springboot.entity.User;
import com.ijse.AI_Coding_Tutor_Springboot.enumerations.UserStatus;
import com.ijse.AI_Coding_Tutor_Springboot.repository.AdminRepository;
import com.ijse.AI_Coding_Tutor_Springboot.repository.UserRepository;
import com.ijse.AI_Coding_Tutor_Springboot.service.AdminService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final UserRepository userRepository;
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public AdminServiceImpl(AdminRepository adminRepository, UserRepository userRepository) {
        this.adminRepository = adminRepository;
        this.userRepository = userRepository;
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

    public void saveAdmin(AdminDTO adminDTO) {
        try{
            User user = new User();
            user.setUserName(adminDTO.getAdminEmail());
            user.setPassword(encoder.encode(adminDTO.getAdminPassword()));
            user.setUserRole("ADMIN");
            user.setUserStatus(UserStatus.ACTIVE);
            user.setJoinedDate(LocalDateTime.now());

            Admin admin = new Admin();
            admin.setAdminFullName(adminDTO.getAdminFullName());
            admin.setAdminContact(adminDTO.getAdminContact());
            admin.setUser(user);
            user.setAdmin(admin);
            userRepository.save(user);
        }
        catch(Exception e){
            throw e;
        }
    }

    public List<GetAdminDetailsDTO> getAdminDetails() {
        try{
            List<GetAdminDetailsDTO> adminList = adminRepository.getAdminDetails();
            return adminList;
        }
        catch(Exception e){
            throw e;
        }
    }
}
