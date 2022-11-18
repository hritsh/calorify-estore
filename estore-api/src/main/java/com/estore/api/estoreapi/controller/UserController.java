package com.estore.api.estoreapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.estore.api.estoreapi.persistence.UserDAO;
import com.estore.api.estoreapi.service.UserService;
import com.estore.api.estoreapi.model.Customer;
import com.estore.api.estoreapi.model.User;

/**
 * Handles the REST API requests for the User resource
 * <p>
 * {@literal @}RestController Spring annotation identifies this class as a REST
 * API
 * method handler to the Spring framework
 * 
 * @author Team-E
 */
@RestController
@RequestMapping("users")
public class UserController {
    private UserDAO userDao;
    private static final Logger LOG = Logger.getLogger(UserController.class.getName());
    @Autowired
    private UserService userService;

    /**
     * Creates a REST API controller to responds to requests
     * 
     * @param userDao The {@link userDAO User Data Access Object} to
     *                perform CRUD operations related to User
     *                <br>
     *                This dependency is injected by the Spring Framework
     */
    public UserController(UserDAO userDao) {
        this.userDao = userDao;
    }

    /**
     * Responds to the GET request for a {@linkplain User user} for the given
     * username
     * 
     * @param id The username used to locate the {@link User user}
     * 
     * @return ResponseEntity with {@link User user} object and HTTP status of OK if
     *         found<br>
     *         ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("/{username:[a-zA-Z &+-]*}")
    // makes sure logged in user cannot access other users details
    @PreAuthorize("#username == authentication.name")
    public ResponseEntity<User> getUser(@PathVariable("username") String username) {
        LOG.info("GET /users/" + username);
        try {
            User user = userDao.getUser(username);
            if (user != null)
                return new ResponseEntity<User>(user, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Gets an array of the {@linkplain User users}
     * 
     * @return an array of the {@link User user} in the database
     */
    @GetMapping("")
    public ResponseEntity<User[]> getUsers() {
        LOG.info("GET /users");

        return new ResponseEntity<User[]>(userDao.getUsers(), HttpStatus.OK);
    }

    /**
     * Adds a new {@linkplain User user} to the database
     * 
     * @param user the {@link User user} object that will be added to file
     * 
     * @return the newly created {@link User user} as well as a
     *         200 (OK) indicating that the action was successful
     *         404 (NOT_FOUND) if the action failed
     *         500 (INTERNAL_SERVER_ERROR) if an issue arouse
     */
    @PostMapping("")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        LOG.info("POST /users" + user.getUsername());
        try {
            User result = userService.registerNewUser(user);
            if (result != null) {
                return new ResponseEntity<User>(result, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Updates the {@linkplain User user} in users.json with the provided
     * {@linkplain Customer customer}
     * object and its details, if it exists
     * 
     * @param customer The {@link Customer customer} to update
     * 
     * @return ResponseEntity with updated {@link Customer customer} object and HTTP
     *         status
     *         of OK if updated<br>
     *         ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PutMapping("")
    @PreAuthorize("#customer.getUsername() == authentication.name")
    public ResponseEntity<Customer> updateUser(@RequestBody Customer customer) {
        LOG.info("PUT /users" + customer.getUsername());

        try {
            Customer updatedCustomer = userDao.updateUserDetails(customer);
            if (updatedCustomer != null)
                return new ResponseEntity<Customer>(customer, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{username}/salad/{salad}")
    public ResponseEntity<String> setSalad(@PathVariable("username") String username,
            @PathVariable("salad") String salad) {
        LOG.info("PUT /user=" + username + "/salad=" + salad);
        try {
            userDao.setSalad(username, salad);
            return new ResponseEntity<String>(HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{username}/salad")
    public ResponseEntity<String> getSalad(@PathVariable("username") String username) {
        LOG.info("GET /user=" + username + "/salad");
        try {
            String salad = userDao.getSalad(username);
            return new ResponseEntity<String>(salad, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Deletes a {@linkplain User user} with the given username
     * 
     * @param username The username of the {@linkplain User user} to be deleted
     * 
     * @return ResponseEntity HTTP status of OK if deleted<br>
     *         ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @DeleteMapping("/{username}")
    @PreAuthorize("#username == authentication.name")
    public ResponseEntity<String> deleteUser(@PathVariable("username") String username) {
        LOG.info("DELETE /user=" + username);
        try {
            boolean deleted = userDao.deleteUser(username);
            if (deleted) {
                return new ResponseEntity<String>(username, HttpStatus.OK);
            } else {
                return new ResponseEntity<String>(username, HttpStatus.NOT_FOUND);
            }
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}