package com.estore.api.estoreapi.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estore.api.estoreapi.model.Role;
import com.estore.api.estoreapi.persistence.RoleDAO;

@Service
public class RoleService {
    @Autowired
    private RoleDAO roleDAO;
    //keep in fileDAO
    public Role createNewRole(Role role) throws IOException {
        return roleDAO.createRole(role);
    }
}
