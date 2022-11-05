package com.estore.api.estoreapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.io.IOException;
import java.util.*;

import com.estore.api.estoreapi.persistence.InventoryDAO;
import com.estore.api.estoreapi.persistence.RoleDAO;
import com.estore.api.estoreapi.service.RoleService;
import com.estore.api.estoreapi.model.Product;
import com.estore.api.estoreapi.model.Role;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Test the Inventory Controller class
 * 
 * @author Team-E
 */

@Tag("Controller-tier")
public class RoleControllerTest {
    private RoleController roleController;
    private RoleDAO mockRoleDAO;

    /**
     * Before each test, create a new roleController object and inject
     * a mock Inventory DAOateProduct
     */
    @BeforeEach
    public void setupRoleController() {
        mockRoleDAO = mock(RoleDAO.class);
        roleController = new RoleController(mockRoleDAO);
    }

    @Test
    public void testcreateRole() throws IOException { // createRole may throw IOException
        // Setup
        Role role = new Role(1, "admin");
        // When the same id is passed in, our mock product DAO will return the product
        // object
        when(mockRoleDAO.createRole(role)).thenReturn(role);

        // Invoke
        ResponseEntity<Role> response = roleController.createNewRole(role);

        // Analyze
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void testcreateRoleFailed() throws IOException { // createRole may throw IOException
        // Setup
        Role product = new Role(1, "admin");
        // when createRole is called, return false simulating failed
        // creation and save
        when(mockRoleDAO.createRole(product)).thenReturn(null);

        // Invoke
        ResponseEntity<Role> response = roleController.createNewRole(product);

        // Analyze
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    public void testcreateRoleHandleException() throws IOException { // createHProduct may throw IOException
        // Setup
        Role product = new Role(1, "admin");

        // When createRole is called on the Mock Inventory DAO, throw an IOException
        doThrow(new IOException()).when(mockRoleDAO).createRole(product);

        // Invoke
        ResponseEntity<Role> response = roleController.createNewRole(product);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testDeleteRole() throws IOException { // DeleteRole may throw IOException
        // Setup
        int productId = 10;
        // when deleteInventory is called return true, simulating successful deletion
        when(mockRoleDAO.deleteRole(productId)).thenReturn(true);

        // Invoke
        ResponseEntity<Role> response = roleController.deleteRole(productId);

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testDeleteRoleNotFound() throws IOException { // DeleteRole may throw IOException
        // Setup
        int productId = 10;
        // when DeleteRole is called return false, simulating failed deletion
        when(mockRoleDAO.deleteRole(productId)).thenReturn(false);
        // Invoke
        ResponseEntity<Role> response = roleController.deleteRole(productId);
        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testDeleteRoleHandleException() throws IOException { // DeleteRole may throw IOException
        // Setup
        int productId = 10;
        // When DeleteRole is called on the Mock Product DAO, throw an IOException
        doThrow(new IOException()).when(mockRoleDAO).deleteRole(productId);

        // Invoke
        ResponseEntity<Role> response = roleController.deleteRole(productId);
    }

    @Test
    public void testGetAllRoles() throws IOException {

        Role[] roles = new Role[2];
        roles[0] = new Role(1, "admin");
        roles[1] = new Role(2, "user");
        // When get all products is called return the products created above
        when(mockRoleDAO.getRoles()).thenReturn(roles);

        // Invoking
        ResponseEntity<Role[]> response = roleController.getRoles();

        // Analyzing
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(roles, response.getBody());
    }

    @Test
    public void testGetAllProductsHandleException() throws IOException { // getProducts may throw IOException
        // When getProducts is called on the Mock Product DAO, throw an IOException
        doThrow(new IOException()).when(mockRoleDAO).getRoles();

        // Invoke
        ResponseEntity<Role[]> response = roleController.getRoles();

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}
