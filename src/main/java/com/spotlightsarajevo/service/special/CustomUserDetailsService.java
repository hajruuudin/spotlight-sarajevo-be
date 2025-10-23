package com.spotlightsarajevo.service.special;

import com.spotlightsarajevo.dao.access.UserDAO;
import com.spotlightsarajevo.dao.entity.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private UserDAO userDAO;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try{
            UserEntity user = userDAO.findBySysEmailOrGoogleEmail(email);
            String userUsername;
            String userRole;
            String userPassword;

            if(user.getSysEmail().length() > 1){
                userUsername = user.getSysEmail();
            } else if (user.getGoogleEmail().length() > 1){
                userUsername = user.getGoogleEmail();
            } else {
                throw new RuntimeException("Email not identified: SPRING SECURITY");
            }

            if(user.getIsAdmin()){
                userRole = "ADMIN";
            } else {
                userRole = "USER";
            }

            if(user.getSysEmail().length() > 1){
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
