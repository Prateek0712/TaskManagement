package com.TaskManagement.TaskManagement.Service;

import com.TaskManagement.TaskManagement.Enity.User;
import com.TaskManagement.TaskManagement.Reposiotory.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private  UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public CustomUserDetailsService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
            Optional<User>optionalUser=userRepo.findByEmail(email);
            if(optionalUser.isPresent())
            {
                User user=optionalUser.get();
                return org.springframework.security.core.userdetails.User.builder()
                        .username(user.getEmail())
                        .password(user.getPassword())
                        .roles(String.valueOf(user.getRol()))
                        .build();
            }
            else
            {
                throw  new UsernameNotFoundException("Given email ID  not  exist  in system");
            }
    }
}
