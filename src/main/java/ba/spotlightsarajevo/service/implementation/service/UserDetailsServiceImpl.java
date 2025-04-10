package ba.spotlightsarajevo.service.implementation.service;

import ba.spotlightsarajevo.dao.UserDAO;
import ba.spotlightsarajevo.dao.entities.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    UserDAO userDAO;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity user = userDAO.findByEmail(email); // Assuming findByEmail exists

        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

        List<String> roles = new ArrayList<>();
        if (user.getIsAdmin()) {
            roles.add("ADMIN");
        } else {
            roles.add("USER");
        }

        System.out.println("Before getting details: user is: " + user);

        String detailPassword = user.getRegistrationType().equals("google") ? "GOOGLE-USER" : user.getPassword();
        String detailEmail = user.getRegistrationType().equals("google") ? user.getGoogleEmail() : user.getEmail();

        System.out.println(detailEmail);
        System.out.println(detailPassword);
        return new User(
                detailEmail,
                detailPassword,
                roles.stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role)).collect(Collectors.toList()));
    }
}
