package com.ijse.AI_Coding_Tutor_Springboot.service.impl;

import com.ijse.AI_Coding_Tutor_Springboot.dto.AdminDTO;
import com.ijse.AI_Coding_Tutor_Springboot.dto.GetAdminDetailsDTO;
import com.ijse.AI_Coding_Tutor_Springboot.dto.UpdateAdminDetailsDTO;
import com.ijse.AI_Coding_Tutor_Springboot.entity.Admin;
import com.ijse.AI_Coding_Tutor_Springboot.entity.User;
import com.ijse.AI_Coding_Tutor_Springboot.enumerations.UserStatus;
import com.ijse.AI_Coding_Tutor_Springboot.exceptions.CustomException;
import com.ijse.AI_Coding_Tutor_Springboot.repository.AdminRepository;
import com.ijse.AI_Coding_Tutor_Springboot.repository.UserRepository;
import com.ijse.AI_Coding_Tutor_Springboot.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public AdminServiceImpl(AdminRepository adminRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Admin getAdminNameByUserId(long userId) {
            log.info("Executing method getAdminNameByUserId()");
            try{
                Optional<Admin> optionalAdmin = adminRepository.getAdminByUserId(userId);
                if(!optionalAdmin.isPresent()){
                    throw new CustomException(404,"Sorry, Admin Not Found");
                }
                Admin admin = optionalAdmin.get();
                return admin;
            }
            catch(Exception e){
                log.error("Error in getAdminNameByUserId()",e);
                throw e;
            }
    }

    public void saveAdmin(AdminDTO adminDTO) {
        log.info("Executing method saveAdmin()");
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
            log.error("Error in saveAdmin()",e);
            throw e;
        }
    }

    public List<GetAdminDetailsDTO> getAdminDetails() {
        log.info("Executing method getAdminDetails()");
        try{
            List<GetAdminDetailsDTO> adminList = adminRepository.getAdminDetails();
            return adminList;
        }
        catch(Exception e){
            log.error("Error in getAdminDetails()",e);
            throw e;
        }
    }

    public GetAdminDetailsDTO getAdminDetailById(long userId) {
        log.info("Executing method getAdminDetailById()");
        try{
            Optional<Admin> optionalAdmin = adminRepository.getAdminByUserId(userId);
            if(!optionalAdmin.isPresent()){
                throw new CustomException(404,"Sorry, Admin Not Found");
            }
            Admin admin = optionalAdmin.get();
            System.out.println("Admin contact - " + admin.getAdminContact());
            return new GetAdminDetailsDTO(admin.getAdminFullName(), admin.getUser().getUserName(), admin.getUser().getUserStatus(), admin.getAdminContact());
        }
        catch(Exception e){
            log.error("Error in getAdminDetailById()",e);
            throw e;
        }
    }

    @Transactional
    public void updateAdmin(UpdateAdminDetailsDTO updateAdminDetailsDTO) {
        log.info("Executing method updateAdmin()");
        try{
            Optional<Admin> optionalAdmin = adminRepository.getAdminByUserId(updateAdminDetailsDTO.getUserId());
            if(!optionalAdmin.isPresent()){
                throw new CustomException(404,"Sorry, Admin Not Found");
            }
            Admin admin = optionalAdmin.get();

            if(updateAdminDetailsDTO.getAdminName() != null){
                admin.setAdminFullName(updateAdminDetailsDTO.getAdminName());
            }

            if(updateAdminDetailsDTO.getAdminContact() != null){
                admin.setAdminContact(updateAdminDetailsDTO.getAdminContact());
            }

            adminRepository.save(admin);

            if(!updateAdminDetailsDTO.getOldPassword().isEmpty() && !updateAdminDetailsDTO.getNewPassword().isEmpty()){
                Optional<User> optionalUser = userRepository.findById(updateAdminDetailsDTO.getUserId());
                if(!optionalUser.isPresent()){
                    throw new CustomException(404,"Sorry, User Not Found");
                }
                User user = optionalUser.get();

                String existingPassword = user.getPassword();
                String oldPassword = updateAdminDetailsDTO.getOldPassword();

                if (!passwordEncoder.matches(existingPassword, oldPassword)) {
                    throw new CustomException(404,"Sorry, Old Password does not match");
                }

                user.setPassword(encoder.encode(updateAdminDetailsDTO.getNewPassword()));
                userRepository.save(user);
            }
        }
        catch(Exception e){
            log.error("Error in updateAdmin()",e);
            throw e;
        }
    }

    public void deleteAdmin(long userId) {
        log.info("Executing method deleteAdmin()");
        try{
            Optional<User> optionalUser = userRepository.findById(userId);
            if(!optionalUser.isPresent()){
                throw new CustomException(404,"Sorry, User Not Found");
            }
            User user = optionalUser.get();
            user.setUserStatus(UserStatus.INACTIVE);
            userRepository.save(user);
        }
        catch(Exception e){
            log.error("Error in deleteAdmin()",e);
            throw e;
        }
    }
}
