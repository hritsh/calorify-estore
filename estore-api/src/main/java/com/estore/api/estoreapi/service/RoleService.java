package com.estore.api.estoreapi.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estore.api.estoreapi.model.Role;
import com.estore.api.estoreapi.persistence.RoleDAO;
/**
 * This file provides services related to Role, it is used to provide roleDAOs methods to create a new role
 * 
 * {@Literal @Service} is a spring annotation that indicates that this class is in the Service layer, it is marked as a service provider
 * is to be instantiated
 * 
 * @author Team E
 */
@Service
public class RoleService {
    @Autowired
    private RoleDAO roleDAO;
    /**
     * Creates a new Role in roles file with Role object provided
     * 
     * @param role Given role object that will be created
     * 
     * @return a {@linkplain Role} object indicating if the operation was successful
     * 
     * @throws IOException if there was issue opening the file
     */
    public Role createNewRole(Role role) throws IOException {
        return roleDAO.createRole(role);
    }
}
