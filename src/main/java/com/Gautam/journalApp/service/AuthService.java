package com.Gautam.journalApp.service;

import com.Gautam.journalApp.entity.User;
import com.Gautam.journalApp.enums.AuthProviderType;
import com.Gautam.journalApp.repository.UserRepository;
import com.Gautam.journalApp.utilies.AuthUtil;
import com.Gautam.journalApp.utilies.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class AuthService {

    @Autowired
    private AuthUtil authUtil;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public ResponseEntity<String> handleOAuth2Login(OAuth2User oAuthUser, String registrationId) {
        //fetch provider type and provider id

        AuthProviderType providerType = authUtil.getAuthProviderType(registrationId);
        String providerId = authUtil.determineProviderIdFromOAuth2User(registrationId, oAuthUser);

        //save the providerType and id info with user

        User user = userRepository.findByProviderIdAndAuthProviderType(providerId, providerType);

        String email = oAuthUser.getAttribute("email");

        User emailUser = userRepository.findByEmail(email);

        if(user == null && emailUser == null)
        {
            //Signup flow:
            String userName = authUtil.determineUserNameFromOauth2User(registrationId, oAuthUser, providerId);
            user = userService.saveNewUserWithoutPassword(userName,email,providerId,providerType);
        }
        else if(user != null)
        {
            if(email != null && !email.isBlank() && !email.equals(user.getEmail()))
            {
                user.setEmail(email);
                userService.saveUser(user);
            }
        }
        else {
            throw new BadCredentialsException("This email is already registered with provider "+ email + " Please login with " + emailUser.getAuthProviderType() + " or use another email to login with " + registrationId);
        }
        //if user has an account : directly login
        //else first signup then login
        String jwtToken = jwtUtil.generateToken(user.getUserName());
        return ResponseEntity.ok(jwtToken);
    }
}
