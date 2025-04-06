package backend.apiscart.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService{


    @Value("${jwt.pass}")
    private String password;

    @Value("${jwt.user}")
    private String userName;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    	String encodedPassword = passwordEncoder.encode(password);
        if (userName.equals(username)) {
            return User.withUsername(userName)
                    .password(encodedPassword)
                    .roles("USER")
                    .build();
        } else {
            throw new UsernameNotFoundException("Usuario no encontrado: " + username);
        }
    }

}
