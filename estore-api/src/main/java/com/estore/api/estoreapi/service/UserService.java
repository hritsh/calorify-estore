package com.estore.api.estoreapi.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.estore.api.estoreapi.model.User;
import com.estore.api.estoreapi.persistence.RoleDAO;
import com.estore.api.estoreapi.persistence.UserDAO;

@Service
public class UserService {
    private UserDAO userDAO; 
    private RoleDAO roleDAO;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public UserService(UserDAO userDAO, RoleDAO roleDAO) {
        this.userDAO = userDAO;
        this.roleDAO = roleDAO;
    }
    public User registerNewUser(User user) throws IOException {
        user.setPassword(getEncodedPassword(user.getPassword()));
        return userDAO.addUser(user);
    }
    public String getEncodedPassword(String password) {
        return passwordEncoder.encode(password);
    }
}
