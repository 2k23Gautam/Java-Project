package com.Gautam.journalApp.utilies;

import com.Gautam.journalApp.enums.AuthProviderType;
import com.Gautam.journalApp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AuthUtil {


    public AuthProviderType getAuthProviderType(String registrationId) {
        return switch (registrationId.toLowerCase()) {
            case "google" -> AuthProviderType.GOOGLE;
            case "facebook" -> AuthProviderType.FACEBOOK;
            case "github" -> AuthProviderType.GITHUB;
            default -> throw new IllegalArgumentException("Unsupported registrationId: " + registrationId);
        };
    }

    public String determineProviderIdFromOAuth2User(String regisrationId, OAuth2User oAuth2User) {
        String providerId = switch (regisrationId.toLowerCase()) {
            case "google" -> oAuth2User.getAttribute("sub");
            case "facebook" -> oAuth2User.getAttribute("id");
            case "github" -> oAuth2User.getAttribute("id").toString();
            default -> {
                log.error("Unsupported registrationId: {}", regisrationId);
                throw new IllegalArgumentException("Unsupported registrationId: " + regisrationId);
            }
        };

        if(providerId == null || providerId.isEmpty() || providerId.isBlank()) {
            log.error("Unable to determine providerId for provider: {}", regisrationId);
            throw new IllegalArgumentException("Unable to determine providerId for OAuth2 login " + regisrationId);
        }

        return providerId;
    }

    public String determineUserNameFromOauth2User(String registrationId, OAuth2User oAuth2User,String providerId) {
        String email = oAuth2User.getAttribute("email");

        if(email != null && !email.isEmpty() && !email.isBlank()) {
            return email.split("@")[0];
        }

        return switch(registrationId.toLowerCase()){
            case "google" -> oAuth2User.getAttribute("sub");
            case "facebook" -> oAuth2User.getAttribute("id");
            case "github" -> oAuth2User.getAttribute("login");
            default -> providerId;
        };
    }
}

