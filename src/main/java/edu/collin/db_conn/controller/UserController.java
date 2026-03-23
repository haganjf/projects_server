package edu.collin.db_conn.controller;

import edu.collin.db_conn.helper.CustomUserDetailsService;
import edu.collin.db_conn.helper.JwtUtil;
import edu.collin.db_conn.model.User;
import edu.collin.db_conn.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private EmailService emailService;
    private final UserRepository repository;

    @Autowired
    public UserController(UserRepository repository) {
        this.repository = repository;
    }

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> request) {
        User user = new User();
        user.setEmail(request.get("email"));
        user.setPassword(request.get("password"));
  //      try {
         authManager.authenticate(
          new UsernamePasswordAuthenticationToken(
           user.getEmail(),
           user.getPassword()
          )
         );
         String token = jwtUtil.generateToken(user.getEmail());
         return Map.of("token", token);
  //      } catch (Exception x) {
  //      return Map.of("Error", x.toString());
  //     }
    }

    @GetMapping("/get")
    public List<User> getAll() {
        return repository.findAll();
    }

    @GetMapping("/confirm")
    public boolean confirm(String name, String email, Long id) {
        if (id > 0) {
            return true;
        } else {
            return false;
        }
    }

    @PostMapping("/register")
    public User create(@RequestBody User user) {
        User result;
              try {
               // emailService.sendEmail(result.getEmail(), result.getName(), result.getId());
                if (user.isValidUser()) {
                 return  userDetailsService.register(user);
                } else {
                    return user;
                }
            }
            catch (Exception x) {
                user.setErrorCode(x.getMessage());
            }
        return user;
    }
}
