package com.TaskManagement.TaskManagement.Controller;

import com.TaskManagement.TaskManagement.RequestDto.JwtRequest;
import com.TaskManagement.TaskManagement.ResponceDto.JwtResponse;
import com.TaskManagement.TaskManagement.Service.CustomUserDetailsService;
import com.TaskManagement.TaskManagement.securityCongifuration.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private CustomUserDetailsService service;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity authenticateAndGetToken(@RequestBody JwtRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            UserDetails userDetails= service.loadUserByUsername(authRequest.getEmail());
            JwtResponse jwtResponse= JwtResponse.builder()
                    .jwtToken(jwtService.generateToken(authRequest.getEmail()))
                    .email(userDetails.getUsername())
                    .build();
            return new ResponseEntity<>(jwtResponse,HttpStatus.OK);
        } else {
            throw new UsernameNotFoundException("invalid user request !");
        }

    }

}
