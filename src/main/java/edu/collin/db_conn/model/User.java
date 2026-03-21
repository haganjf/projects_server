package edu.collin.db_conn.model;

import edu.collin.db_conn.helper.EmailValidator;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

     private String password;

     private String errorCode;

    public User() {}

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getErrorCode() {
       return errorCode;
    }

    public boolean isValidUser() {
        if ((this.getName() != null) && (this.getEmail() != null)) {
            if (this.password.length() > 7) {
                if (EmailValidator.isValidEmail(this.getEmail())) {
                    return true;
                } else {
                    this.errorCode = "Email format is not valid.";
                    return false;
                }
            } else {
                this.errorCode = "Password must be greater than 7 characters.";
                return false;
            }
        } else {
            this.errorCode = "Name and password must both be populated.";
            return false;
        }
    }

    public boolean isRegistered(List<User> users) {
        for (User user : users) {
            if (user.getEmail().equals(this.email)) { // check password
                if (user.getPassword().equals(this.password)) { // user is registered
                    return true;
                } else { // password does not match
                    return false;
                }
            } // end if email matches
        }
        return false; // there was no match
    }
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}