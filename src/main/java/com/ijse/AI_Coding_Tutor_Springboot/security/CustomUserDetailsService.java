package com.ijse.AI_Coding_Tutor_Springboot.security;

import com.ijse.AI_Coding_Tutor_Springboot.repository.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
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

        String userRolesStr = optionalUser.get().getUserRole();
        String[] roles = new String[0];
        if (userRolesStr != null && !userRolesStr.trim().isEmpty()) {
            roles = Arrays.stream(userRolesStr.split(","))
                    .map(String::trim)
                    .map(role -> role.startsWith("ROLE_") ? role.substring(5) : role)
                    .filter(role -> !role.isEmpty())
                    .toArray(String[]::new);
        }

        return User.builder()
                .username(optionalUser.get().getUserName())
                .password(optionalUser.get().getPassword())
                .roles(optionalUser.get().getUserRole())
                .build();
    }
}