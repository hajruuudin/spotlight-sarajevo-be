package com.spotlightsarajevo.service.special;

import com.spotlightsarajevo.dao.access.UserDAO;
import com.spotlightsarajevo.dao.entity.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private UserDAO userDAO;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try{
            Optional<UserEntity> optionalUser = userDAO.findBySysEmailOrGoogleEmail(email);
            String userUsername;
            String userRole;
            String userPassword;

            UserEntity user = new UserEntity();

            if(optionalUser.isPresent()){
                user = optionalUser.get();
            } else {
                throw new RuntimeException("User not found");
            }

            if(user.getRegistrationType().equals("SYSTEM")){
                userUsername = user.getSysEmail();
            } else if (user.getRegistrationType().equals("GOOGLE")){
                userUsername = user.getGoogleEmail();
            } else {
                throw new RuntimeException("Email not identified: SPRING SECURITY");
            }

            if(user.getIsAdmin()){
                userRole = "ADMIN";
            } else {
                userRole = "USER";
            }

            if(user.getRegistrationType().equals("SYSTEM")){
                userPassword = user.getSysPassword();
            } else {
                userPassword = "GOOGLE_AUTH_NO_PASS";
            }

            return User.builder()
                    .username(userUsername)
                    .password(userPassword)
                    .roles(userRole)
                    .build();
        } catch (UsernameNotFoundException e){
            e.printStackTrace();
            throw new RuntimeException("USERNAME NOT FOUND - AUTH ERROR");
        }

    }
}
