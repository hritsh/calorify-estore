package com.estore.api.estoreapi.controller;

import java.io.IOException;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.logging.Level;
import org.springframework.web.bind.annotation.RestController;

import com.estore.api.estoreapi.model.Role;
import com.estore.api.estoreapi.persistence.RoleDAO;
import com.estore.api.estoreapi.service.RoleService;
/**
 * Handles the REST API requests for CRUD operations related to Role model resource
 * <p>
 * {@literal @}RestController Spring annotation identifies this class as a REST
 * API
 * method handler to the Spring framework
 * 
 * @author Team-E
 */
@RestController
@RequestMapping("roles")
public class RoleController {
    private static final Logger LOG = Logger.getLogger(InventoryController.class.getName());
    private RoleDAO roleDao;
    @Autowired
    private RoleService roleService;
    /**
     * Creates a REST API controller to respond to requests
     * 
     * @param roleDao The {@link RoleDAO Role Data Access Object} to be used in operations relating to RoleFileDAO and roles.json
     *                     <br>
     *                     This dependency is injected by the Spring Framework
     */
    public RoleController(RoleDAO roleDao) {
        this.roleDao = roleDao;
    }
    /**
     * Responds to the GET request for all {@linkplain Role roles}
     * 
     * @return ResponseEntity with array of {@link Role roles} objects (may be
     *         empty)
     *         and
     *         HTTP status of OK<br>
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("")
    public ResponseEntity<Role[]> getRoles() {
        LOG.info("GET /roles");
        try {
            Role[] roles = roleDao.getRoles();
            return new ResponseEntity<Role[]>(roles, HttpStatus.OK);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    /**
     * Creates a {@linkplain Role role} with the provided Role object
     * 
     * @param role - The {@link Role role} to create
     * 
     * @return ResponseEntity with created {@link Role role} object and HTTP
     *         status
     *         of CREATED<br>
     *         ResponseEntity with HTTP status of CONFLICT if {@link Role
     *         role}
     *         object already exists<br>
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PostMapping({"/"})
    public ResponseEntity<Role> createNewRole(@RequestBody Role role) {
        LOG.info("POST /roles " + role.getRoleName());
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

    /**
     * Deletes a {@linkplain Role role} with the given id
     * 
     * @param id The id of the {@link Role role} to deleted
     * 
     * @return ResponseEntity HTTP status of OK if deleted<br>
     *         ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Role> deleteRole(@PathVariable int id) {
        LOG.info("DELETE /roles/" + id);
        try {
            boolean role = roleDao.deleteRole(id);
            if (role != false)
                return new ResponseEntity<>(HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
