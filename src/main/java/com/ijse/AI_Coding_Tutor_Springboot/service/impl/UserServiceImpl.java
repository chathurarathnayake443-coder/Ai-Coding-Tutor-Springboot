package com.ijse.AI_Coding_Tutor_Springboot.service.impl;

import com.ijse.AI_Coding_Tutor_Springboot.dto.StudentDTO;
import com.ijse.AI_Coding_Tutor_Springboot.dto.UserDTO;
import com.ijse.AI_Coding_Tutor_Springboot.entity.Student;
import com.ijse.AI_Coding_Tutor_Springboot.entity.SubscriptionPlan;
import com.ijse.AI_Coding_Tutor_Springboot.entity.User;
import com.ijse.AI_Coding_Tutor_Springboot.entity.UserSubscription;
import com.ijse.AI_Coding_Tutor_Springboot.enumerations.UserStatus;
import com.ijse.AI_Coding_Tutor_Springboot.enumerations.UserSubscriptionPlanStatus;
import com.ijse.AI_Coding_Tutor_Springboot.repository.SubscriptionPlanRepository;
import com.ijse.AI_Coding_Tutor_Springboot.repository.UserRepository;
import com.ijse.AI_Coding_Tutor_Springboot.repository.UserSubscriptionRepository;
import com.ijse.AI_Coding_Tutor_Springboot.service.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private SubscriptionPlanRepository subscriptionPlanRepository;
    private UserSubscriptionRepository userSubscriptionRepository;

    public UserServiceImpl(UserRepository userRepository, SubscriptionPlanRepository subscriptionPlanRepository, UserSubscriptionRepository userSubscriptionRepository) {
        this.userRepository = userRepository;
        this.subscriptionPlanRepository = subscriptionPlanRepository;
        this.userSubscriptionRepository = userSubscriptionRepository;
    }

    public UserDTO getUserDetails(String userName, String password, String userRole) {
        try{
            Optional<User> optionalUser = userRepository.findByUserNameAndPassword(userName, password);
            if(!optionalUser.isPresent()){
                throw new RuntimeException("Sorry, User Not Found");
            }
            User user = optionalUser.get();

            if(!user.getUserRole().contains(userRole.toUpperCase())){
                throw new RuntimeException("Sorry, Invalid UserRole");
            }

            return new UserDTO(user.getUserId(), user.getUserName(),user.getPassword(),user.getUserStatus(),user.getJoinedDate(),user.getUserRole());
        }
        catch(Exception e){
            throw e;
        }
    }

    public void signupUserStudent(StudentDTO studentDTO) {
        try{
            User user = new User();

            user.setUserName(studentDTO.getStudentEmail());
            user.setPassword(studentDTO.getPassword());
            user.setUserStatus(UserStatus.ACTIVE);
            user.setUserRole("STUDENT");
            user.setJoinedDate(LocalDateTime.now());

            Student student = new Student();
            student.setStudentFullName(studentDTO.getStudentFullName());
            student.setStudentContact(studentDTO.getStudentContact());

            Optional<SubscriptionPlan> optionalSubscriptionPlan = subscriptionPlanRepository.getFreeSubscriptionPlanId();

            if(!optionalSubscriptionPlan.isPresent()){
                throw new RuntimeException("Sorry, SubscriptionPlan Not Found");
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
            throw e;
        }
    }
}
