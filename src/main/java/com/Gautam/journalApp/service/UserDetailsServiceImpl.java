package com.Gautam.journalApp.service;

import com.Gautam.journalApp.entry.User;
import com.Gautam.journalApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    //called during login by spring automatically
    //Spring calls this automatically during authentication.
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username);

        if(user != null)
        {
            //you convert YOUR User object into Spring Security’s UserDetails.
            UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                    .username(user.getUserName())
                    .password(user.getPassword())
                    .roles(user.getRoles().toArray(new String[0]))
                    .build();


            return userDetails;
        }
        throw new UsernameNotFoundException("User not found with userName: " + username);
    }
}
