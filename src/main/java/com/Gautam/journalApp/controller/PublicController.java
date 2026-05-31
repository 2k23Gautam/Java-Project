package com.Gautam.journalApp.controller;

import com.Gautam.journalApp.entry.User;
import com.Gautam.journalApp.repository.UserRepository;
import com.Gautam.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping("/public")
public class PublicController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;


    @PostMapping("/create-user")
    public Boolean addUser(@RequestBody User newUser){
        return userService.saveNewUser(newUser);
    }

}
