package edu.collin.db_conn.controller;

import edu.collin.db_conn.helper.JwtUtil;
import edu.collin.db_conn.model.User;
import edu.collin.db_conn.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*") // Allow requests from all locations
@RequestMapping("/users")
public class UserController {

    @Autowired
    private EmailService emailService;

    private final UserRepository repository;

    public UserController(UserRepository repository) {
        this.repository = repository;
    }

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> request) {

        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.get("email"),
                        request.get("password")
                )
        );

        String token = jwtUtil.generateToken(request.get("email"));
        return Map.of("token", token);
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
