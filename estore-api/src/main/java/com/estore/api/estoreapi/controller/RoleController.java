package com.estore.api.estoreapi.controller;

import java.io.IOException;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.estore.api.estoreapi.model.Role;
import com.estore.api.estoreapi.persistence.RoleDAO;
import com.estore.api.estoreapi.service.RoleService;

@RestController
@RequestMapping("roles")
public class RoleController {
    private static final Logger LOG = Logger.getLogger(InventoryController.class.getName());
    private RoleDAO roleDao;
    @Autowired
    private RoleService roleService;
    public RoleController(RoleDAO roleDao) {
        this.roleDao = roleDao;
    }
    @PostMapping({"/"})
    public ResponseEntity<Role> createNewRole(@RequestBody Role role) {
        LOG.info("POST /roles " + role);
        try {
            Role newRole = roleService.createNewRole(role);
            if (newRole != null)
                return new ResponseEntity<Role>(newRole, HttpStatus.CREATED);
            else
                return new ResponseEntity<>(HttpStatus.CONFLICT);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }
}
