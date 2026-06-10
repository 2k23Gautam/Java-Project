package com.Gautam.journalApp.controller;

import com.Gautam.journalApp.entity.User;
import com.Gautam.journalApp.repository.UserRepository;
import com.Gautam.journalApp.service.UserDetailsServiceImpl;
import com.Gautam.journalApp.service.UserService;
import com.Gautam.journalApp.utilies.JwtUtil;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/public")
public class PublicController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/signup")
    public Boolean signup(@RequestBody User newUser){
        userService.saveNewUser(newUser);
        return true;
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user){
        try{
            //it will use userDetailsserviceimpl and password encoder to check user present or not in database
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword()));
            String jwt = jwtUtil.generateToken(user.getUserName());
            return new ResponseEntity<>(jwt, HttpStatus.OK);
        }catch (Exception e){
            log.error("Exception occurred while create AuthenticationToken ",e);
            return new ResponseEntity<>("Incorrect username or password",HttpStatus.BAD_REQUEST);
//            throw e;
        }
    }

}
