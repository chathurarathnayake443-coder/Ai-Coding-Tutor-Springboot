package com.ijse.AI_Coding_Tutor_Springboot.service.impl;

import com.ijse.AI_Coding_Tutor_Springboot.dto.UserDTO;
import com.ijse.AI_Coding_Tutor_Springboot.entity.User;
import com.ijse.AI_Coding_Tutor_Springboot.repository.UserRepository;
import com.ijse.AI_Coding_Tutor_Springboot.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
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

            return new UserDTO(user.getUserId(), user.getUserName(),user.getPassword(),user.getUserStatus(),user.getUserRole());
        }
        catch(Exception e){
            throw e;
        }
    }
}
