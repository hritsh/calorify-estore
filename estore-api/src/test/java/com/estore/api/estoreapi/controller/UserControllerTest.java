package com.estore.api.estoreapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.io.IOException;
import java.util.*;

import com.estore.api.estoreapi.persistence.InventoryDAO;
import com.estore.api.estoreapi.persistence.UserDAO;
import com.estore.api.estoreapi.model.Customer;
import com.estore.api.estoreapi.model.Product;
import com.estore.api.estoreapi.model.Role;
import com.estore.api.estoreapi.model.User;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Test the User Controller class
 * 
 * @author Team-E
 */
public class UserControllerTest {
    private UserController userController;
    private UserDAO mockUserDAO;

    /**
     * Before each test, create a new userController object and inject
     * a mock Inventory DAO
     */
    @BeforeEach
    public void setupuserController() {
        mockUserDAO = mock(UserDAO.class);
        userController = new UserController(mockUserDAO);
    }

    @Test
    public void testGetUser() throws IOException { // getProduct may throw IOException
        // Setup
        Set<Role> roleSet = new HashSet<>();
        Role r = new Role(1, "admin");
        roleSet.add(r);
        User user = new User("christin", "christin", roleSet);
        // When the same id is passed in, our mock product DAO will return the product
        // object
        when(mockUserDAO.getUser(user.getUsername())).thenReturn(user);

        // Invoke
        ResponseEntity<User> response = userController.getUser(user.getUsername());

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testGetUserNotFound() throws Exception { // getProduct may throw IOException
        // Setup
        String username = "christin";
        // When the same id is passed in, our mock Product DAO will return null,
        // simulating
        // no Product found
        when(mockUserDAO.getUser(username)).thenReturn(null);

        // Invoke
        ResponseEntity<User> response = userController.getUser(username);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testGetUserHandleException() throws Exception { // createProduct may throw IOException
        // Setup
        String username = "christin";
        // When getProduct is called on the Mock Product DAO, throw an IOException
        doThrow(new IOException()).when(mockUserDAO).getUser(username);

        // Invoke
        ResponseEntity<User> response = userController.getUser(username);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testCreateUser() throws IOException {
        // Setup
        Set<Role> roleSet = new HashSet<>();
        Role r = new Role(1, "admin");
        roleSet.add(r);
        User user = new User("christin", "christin", roleSet);
        // When the same id is passed in, our mock product DAO will return the product
        // object
        when(mockUserDAO.addUser(user)).thenReturn(user);

        // Invoke
        ResponseEntity<User> response = userController.addUser(user);

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    public void testCreateUserFailed() throws IOException { // createProduct may throw IOException
        // Setup
        Set<Role> roleSet = new HashSet<>();
        Role r = new Role(1, "admin");
        roleSet.add(r);
        User user = new User("christin", "christin", roleSet);
        // when createProduct is called, return false simulating failed
        // creation and save
        when(mockUserDAO.addUser(user)).thenReturn(null);

        // Invoke
        ResponseEntity<User> response = userController.addUser(user);

        // Analyze
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    public void testCreateUserHandleException() throws IOException { // createHProduct may throw IOException
        // Setup
        Set<Role> roleSet = new HashSet<>();
        Role r = new Role(1, "admin");
        roleSet.add(r);
        User user = new User("christin", "christin", roleSet);

        // When createProduct is called on the Mock Inventory DAO, throw an IOException
        doThrow(new IOException()).when(mockUserDAO).addUser(user);

        // Invoke
        ResponseEntity<User> response = userController.addUser(user);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testUpdateUser() throws IOException { // updateProduct may throw IOException
        // Setup
        Set<Role> roleSet = new HashSet<>();
        Role r = new Role(1, "admin");
        roleSet.add(r);
        Customer customer = new Customer("christin", "christin", roleSet);
        // when updateProduct is called, return true simulating successful
        // update and save
        when(mockUserDAO.updateUserDetails(customer)).thenReturn(customer);
        ResponseEntity<Customer> response = userController.updateUser(customer);
        // customer.setName("Orange");

        // Invoke
        response = userController.updateUser(customer);

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(customer, response.getBody());
    }

    @Test
    public void testUpdateUserFailed() throws IOException { // updateProduct may throw IOException
        // Setup
        Set<Role> roleSet = new HashSet<>();
        Role r = new Role(1, "admin");
        roleSet.add(r);
        Customer customer = new Customer("christin", "christin", roleSet);
        // when updateProduct is called, return true simulating successful
        // update and save
        when(mockUserDAO.updateUserDetails(customer)).thenReturn(null);

        // Invoke
        ResponseEntity<Customer> response = userController.updateUser(customer);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testUpdateUserHandleException() throws IOException { // updateProduct may throw IOException
        // Setup
        Set<Role> roleSet = new HashSet<>();
        Role r = new Role(1, "admin");
        roleSet.add(r);
        Customer customer = new Customer("christin", "christin", roleSet);
        // When updateProduct is called on the Mock Inventory DAO, throw an IOException
        doThrow(new IOException()).when(mockUserDAO).updateUserDetails(customer);

        // Invoke
        ResponseEntity<Customer> response = userController.updateUser(customer);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testDeleteUser() throws IOException {
        // Setup
        String username = "christin";
        // when deleteInventory is called return true, simulating successful deletion
        when(mockUserDAO.deleteUser(username)).thenReturn(true);

        // Invoke
        ResponseEntity<String> response = userController.deleteUser(username);

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testDeleteUserNotFound() throws IOException {
        // Setup
        String username = "christin";
        // when deleteProduct is called return false, simulating failed deletion
        when(mockUserDAO.deleteUser(username)).thenReturn(false);
        // Invoke
        ResponseEntity<String> response = userController.deleteUser(username);
        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testDeleteUserHandleException() throws IOException {
        // Setup
        String username = "christin";
        // When deleteProduct is called on the Mock Product DAO, throw an IOException
        doThrow(new IOException()).when(mockUserDAO).deleteUser(username);

        // Invoke
        ResponseEntity<String> response = userController.deleteUser(username);
    }

    @Test
    public void testGetAllUsers() throws IOException {
        // Setup
        Set<Role> roleSet = new HashSet<>();
        Role r = new Role(1, "admin");
        roleSet.add(r);
        Customer user = new Customer("christin", "christin", roleSet);
        User[] userList;
        // add user to userList
        userList = mockUserDAO.getUsers();
        // when getAllProducts is called, return the list of products

        // Invoke
        ResponseEntity<User[]> response = userController.getUsers();

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userList, response.getBody());
    }

    @Test
    public void testGetAllUsersHandleException() throws IOException {
        // When getAllProducts is called on the Mock Product DAO, throw an IOException
        doThrow(new IOException()).when(mockUserDAO).getUsers();

        // Invoke
        ResponseEntity<User[]> response = userController.getUsers();

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}
