package edu.collin.db_conn.controller;

import edu.collin.db_conn.helper.JwtUtil;
import edu.collin.db_conn.model.User;
import edu.collin.db_conn.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {
    private AuthenticationManager authManager;
    private JwtUtil jwtUtil;
    private UserDetailsService userDetailsService;

    public UserController(AuthenticationManager authManager,
                          JwtUtil jwtUtil,
                          UserDetailsService userDetailsService) {
        this.authManager = authManager;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }
    @Autowired
    private EmailService emailService;

    private UserRepository repository;

    public UserController(UserRepository repository) {
        this.repository = repository;
    }

//    @Autowired
//   private AuthenticationManager authManager;

  //  @Autowired
  //  private JwtUtil jwtUtil;

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> request) {
        User user = new User();
        user.setEmail(request.get("email"));
        user.setPassword(request.get("password"));
        List<User> users = repository.findAll();
        if (user.isRegistered(users)) {
            authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getEmail(),
                            user.getPassword()
                    )
            );
            UserDetails userDetails =
                    userDetailsService.loadUserByUsername(user.getEmail());
            String token = jwtUtil.generateToken(user.getEmail());
            return Map.of("token", token);
        } else {
            return Map.of("Error", "Login invalid");
        }
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
                 result = repository.save(user);
                 return result;
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
