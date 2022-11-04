package com.estore.api.estoreapi.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.estore.api.estoreapi.model.User;
import com.estore.api.estoreapi.persistence.RoleDAO;
import com.estore.api.estoreapi.persistence.UserDAO;
/**
 * This file provides services related to User, it is used to provide register new User methods to create a new User
 * 
 * {@Literal @Service} is a spring annotation that indicates that this class is in the Service layer, it is marked as a service provider
 * is to be instantiated
 * 
 * @author Team E
 */
@Service
public class UserService {
    private UserDAO userDAO; 
    private RoleDAO roleDAO;

    @Autowired
    private PasswordEncoder passwordEncoder;
    /**
     * Constructor for the UserService class
     * 
     * @param userDAO the userDAO which is to access operations related to users file such as creating a new user
     * @param roleDAO the roleDAO which is to access operations related to roles file
     * 
     */
    public UserService(UserDAO userDAO, RoleDAO roleDAO) {
        this.userDAO = userDAO;
        this.roleDAO = roleDAO;
    }
    /**
     * Used to create a new user in users file with given details represented in the User Object, the password is encoded 
     * with passwordEncoder bean before creation
     * 
     * @param user the userDAO which is to access operations related to users file such as creating a new user
     * 
     * @return User the roleDAO which is to access operations related to roles file
     * 
     * @throws IOException 
     */
    public User registerNewUser(User user) throws IOException {
        user.setPassword(getEncodedPassword(user.getPassword()));
        return userDAO.addUser(user);
    }
    /**
     * Encodes a given string with BCrypt passwordEncoder 
     * 
     * @param password given password of a user in plaintext to be encoded
     * 
     * @return password encoded with passwordEncoder bean that uses BCrypt
     * 
     */
    public String getEncodedPassword(String password) {
        return passwordEncoder.encode(password);
    }
}
