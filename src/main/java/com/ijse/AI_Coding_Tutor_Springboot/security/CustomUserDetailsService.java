package com.ijse.AI_Coding_Tutor_Springboot.security;

import com.ijse.AI_Coding_Tutor_Springboot.repository.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<com.ijse.AI_Coding_Tutor_Springboot.entity.User> optionalUser = userRepository.findByUserName(username);

        if(!optionalUser.isPresent()){
            throw new RuntimeException("Sorry no user");
        }


        return User.builder()
                .username(optionalUser.get().getUserName())
                .password(optionalUser.get().getPassword())
                .roles(optionalUser.get().getUserRole())
                .build();
    }
}