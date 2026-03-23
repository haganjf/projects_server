package edu.collin.db_conn.helper;

import edu.collin.db_conn.model.User;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Example;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import edu.collin.db_conn.repository.UserRepository;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Bean
    public PasswordEncoder pEncoder() {
        return new BCryptPasswordEncoder();
    }
    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        User userProbe = new User();
        userProbe.setEmail(email);
        Example<User> example = Example.of(userProbe);
        try {
            Optional<User> match = userRepository.findOne(example);
            User user = match.get();
            return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getEmail())
                    .password(user.getPassword()) // BCrypt hash
                    .roles("USER")
                    .build();
        } catch (Exception x) {
            System.out.println(x.toString());
            return org.springframework.security.core.userdetails.User.builder()
                    .username("email")
                    .password("password") // BCrypt hash
                    .roles("USER")
                    .build();
        }
    }
    public User register(User user) {
        user.setPassword(pEncoder().encode(user.getPassword()));
        User result = userRepository.save(user);
        return result;
    }
}