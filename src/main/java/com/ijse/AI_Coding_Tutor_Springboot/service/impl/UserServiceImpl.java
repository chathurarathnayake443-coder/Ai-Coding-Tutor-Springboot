package com.ijse.AI_Coding_Tutor_Springboot.service.impl;

import com.ijse.AI_Coding_Tutor_Springboot.dto.StudentDTO;
import com.ijse.AI_Coding_Tutor_Springboot.dto.UserDTO;
import com.ijse.AI_Coding_Tutor_Springboot.entity.Student;
import com.ijse.AI_Coding_Tutor_Springboot.entity.SubscriptionPlan;
import com.ijse.AI_Coding_Tutor_Springboot.entity.User;
import com.ijse.AI_Coding_Tutor_Springboot.entity.UserSubscription;
import com.ijse.AI_Coding_Tutor_Springboot.enumerations.UserStatus;
import com.ijse.AI_Coding_Tutor_Springboot.enumerations.UserSubscriptionPlanStatus;
import com.ijse.AI_Coding_Tutor_Springboot.exceptions.CustomException;
import com.ijse.AI_Coding_Tutor_Springboot.repository.SubscriptionPlanRepository;
import com.ijse.AI_Coding_Tutor_Springboot.repository.UserRepository;
import com.ijse.AI_Coding_Tutor_Springboot.repository.UserSubscriptionRepository;
import com.ijse.AI_Coding_Tutor_Springboot.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final SubscriptionPlanRepository subscriptionPlanRepository;
    private final UserSubscriptionRepository userSubscriptionRepository;
    private final PasswordEncoder passwordEncoder;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public UserServiceImpl(UserRepository userRepository, SubscriptionPlanRepository subscriptionPlanRepository, UserSubscriptionRepository userSubscriptionRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.subscriptionPlanRepository = subscriptionPlanRepository;
        this.userSubscriptionRepository = userSubscriptionRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDTO getUserDetails(String userName, String password, String userRole) {
        log.info("Executing method getUserDetails()");
        try{
            Optional<User> optionalUser = userRepository.findByUserName(userName);
            if(!optionalUser.isPresent()){
                throw new CustomException(404,"Sorry, User Not Found");
            }
            User user = optionalUser.get();

            if (!passwordEncoder.matches(password, user.getPassword())) {
                throw new CustomException(422,"Sorry, Invalid Password");
            }

            if(!user.getUserRole().contains(userRole.toUpperCase())){
                throw new CustomException(422,"Sorry, Invalid UserRole");
            }

            return new UserDTO(user.getUserId(), user.getUserName(),user.getPassword(),user.getUserStatus(),user.getJoinedDate(),user.getUserRole());
        }
        catch(Exception e){
            log.error("Error in method getUserDetails()",e);
            throw e;
        }
    }

    public void signupUserStudent(StudentDTO studentDTO) {
        log.info("Executing method signupUserStudent()");
        try{
            User user = new User();

            user.setUserName(studentDTO.getStudentEmail());
            user.setPassword(encoder.encode(studentDTO.getPassword()));
            user.setUserStatus(UserStatus.ACTIVE);
            user.setUserRole("STUDENT");
            user.setJoinedDate(LocalDateTime.now());

            Student student = new Student();
            student.setStudentFullName(studentDTO.getStudentFullName());
            student.setStudentContact(studentDTO.getStudentContact());

            Optional<SubscriptionPlan> optionalSubscriptionPlan = subscriptionPlanRepository.getFreeSubscriptionPlanId();

            if(!optionalSubscriptionPlan.isPresent()){
                throw new CustomException(404,"Sorry, SubscriptionPlan Not Found");
            }

            SubscriptionPlan subscriptionPlan = optionalSubscriptionPlan.get();

            UserSubscription userSubscription = new UserSubscription();
            userSubscription.setUserPlanStatus(UserSubscriptionPlanStatus.ACTIVE);
            userSubscription.setStartDate(LocalDateTime.now());
            userSubscription.setEndDate(LocalDateTime.now().plusDays(30));
            userSubscription.setSubscriptionPlan(subscriptionPlan);

            userSubscription = userSubscriptionRepository.save(userSubscription);

            student.setUserSubscription(userSubscription);
            student.setUser(user);
            user.setStudent(student);

            userRepository.save(user);
        }
        catch(Exception e){
            log.error("Error in method signupUserStudent()",e);
            throw e;
        }
    }

    public UserDTO findOrCreateByGoogleEmail(String email, String name) {
        log.info("Executing method findOrCreateByGoogleEmail()");
        try{
            Optional<User> existing = userRepository.findByUserName(email);
            if (existing.isPresent()) {
                User user = existing.get();
                UserDTO userDTO = new UserDTO();
                userDTO.setUserId(user.getUserId());
                userDTO.setUserName(user.getUserName());
                userDTO.setPassword(user.getPassword());
                userDTO.setUserStatus(user.getUserStatus());
                userDTO.setJoinedDate(user.getJoinedDate());
                return userDTO;
            }

            User user = new User();
            user.setUserName(email);
            user.setPassword(passwordEncoder.encode(UUID.randomUUID().toString()));
            user.setUserRole("STUDENT");
            user.setUserStatus(UserStatus.ACTIVE);
            user.setJoinedDate(LocalDateTime.now());

            Student student = new Student();
            student.setStudentFullName(name);
            student.setUser(user);
            user.setStudent(student);
            userRepository.save(user);
            return null;
        }
        catch(Exception e){
            log.error("Error in method findOrCreateByGoogleEmail()",e);
            throw e;
        }
    }

    public UserStatus getUserStatus(String userName) {
        log.info("Executing method getUserStatus()");
        try{
            UserStatus userStatus = userRepository.getUserStatus(userName);
            return userStatus;
        }
        catch(Exception e){
            log.error("Error in method getUserStatus()",e);
            throw e;
        }
    }
}
