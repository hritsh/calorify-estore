package com.estore.api.estoreapi.controller;

import java.io.IOException;
import java.util.logging.Logger;

import com.estore.api.estoreapi.model.Product;
import com.estore.api.estoreapi.persistence.ShoppingCartDAO;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Handles the REST API requests for the Shopping Cart resource
 * <p>
 * {@literal @}RestController Spring annotation identifies this class as a REST
 * API
 * method handler to the Spring framework
 * 
 * @author Team-E
 */

@RestController
@RequestMapping("shoppingCart")
public class ShoppingCartController {
    private static final Logger LOG = Logger.getLogger(ShoppingCartController.class.getName());
    private ShoppingCartDAO shoppingCartDao;

    /**
     * Creates a REST API controller to responds to requests
     * 
     * @param shoppingCartDAO The {@link ShoppingCartDAO Shopping Cart Data Access Object}
     *                        to
     *                        perform CRUD operations
     *                        <br>
     *                        This dependency is injected by the Spring Framework
     */
    public ShoppingCartController(ShoppingCartDAO shoppingCartDAO) {
        this.shoppingCartDao = shoppingCartDAO;
    }

    /**
     * Gets a {@linkplain Product product} array which holds the items inside of a
     * {@link Customer customer's}
     * {@link ShoppingCart cart}
     * 
     * @param username the username of the {@link Customer customer}
     * 
     * @return A ResonseEntity with the {@link Product product} that was obtained
     *         from the id
     *         and a HTTP status code OK
     *         A ResponseEntity with HTTP status of NOT_FOUND if no {@link Product
     *         product} was
     *         found
     *         A ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("/{username}")
    @PreAuthorize("#username == authentication.name")
    public ResponseEntity<Product[]> getCart(@PathVariable("username") String username) {
        LOG.info("GET /shoppingcart/customer=" + username);
        try {
            Product[] cartFound = shoppingCartDao.getShoppingCart(username);
            if (cartFound == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<Product[]>(cartFound, HttpStatus.OK);
            }
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Deletes a {@linkplain Product product} with the given id from the Shopping Cart
     * 
     * @param username the username of the {@link Customer customer} who initiaited
     *                 this action
     * @param id       The id of the {@linkplain Product product}to deleted
     * 
     * @return ResponseEntity HTTP status of OK if deleted<br>
     *         ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @DeleteMapping("/{username}/{id}")
    @PreAuthorize("#username == authentication.name")
    public ResponseEntity<Product> deleteProduct(@PathVariable("username") String username, @PathVariable int id) {
        LOG.info("DELETE /cart/customer=" + username + "/product/id=" + id);
        try {
            boolean deleted = shoppingCartDao.deleteProduct(username, id);
            if (deleted) {
                return new ResponseEntity<Product>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Clears the cart of the {@linkplain Customer customer} who initiated the
     * method
     * 
     * @param username the username of the {@link Customer customer}
     * 
     * @return a boolean indicating of the success of the action as well as a
     *         200 (OK) indicating that the action was successful
     *         404 (NOT_FOUND) if the action failed
     *         500 (INTERNAL_SERVER_ERROR) if an issue arouse
     */
    @DeleteMapping("/{username}")
    @PreAuthorize("#username == authentication.name")
    public ResponseEntity<Boolean> clearCart(@PathVariable("username") String username) {
        LOG.info("DELETE /shoppingcart/customer=" + username);
        try {
            boolean deleted = shoppingCartDao.clearShoppingCart(username);
            if (deleted) {
                return new ResponseEntity<Boolean>(deleted, HttpStatus.OK);
            } else {
                return new ResponseEntity<Boolean>(deleted, HttpStatus.NOT_FOUND);
            }
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * A checkout execution for the {@linkplain Customer customer}
     * 
     * @param username the username of the {@linkplain Customer customer} who wishes
     *                 to checkout
     * 
     * @return A boolean indicating the success of the action as well as a
     *         201 (CREATED) indicating that the action was successful
     *         409 (CONFLICT) if the action failed
     *         500 (INTERNAL_SERVER_ERROR) if an issue arouse
     */
    @PostMapping("/{username}")
    @PreAuthorize("#username == authentication.name")
    public ResponseEntity<Boolean> checkout(@PathVariable("username") String username) {
        LOG.info("POST /shoppingcart/customer=" + username);
        try {
            boolean result = shoppingCartDao.checkout(username);
            if (result) {
                return new ResponseEntity<Boolean>(result, HttpStatus.OK);
            } else {
                return new ResponseEntity<Boolean>(result, HttpStatus.CONFLICT);
            }
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Lets the {@linkplain Customer customer} add a {@link Product product} to
     * their {@link ShoppingCart cart}
     * 
     * @param username the {@link Customer customer} username who initiated the
     *                 action
     * @param quantity the number of items the {@linke Customer customer} is
     *                 attempting to add
     * @param id       the id of the specific {@link Product product} the
     *                 {@link Customer customer} is trying to add
     * 
     * @return the id of the {@link Product product} that was added as well as a
     *         201 (CREATED) indicating that the action was successful
     *         409 (CONFLICT) if the action failed
     *         500 (INTERNAL_SERVER_ERROR) if an issue arouse
     */
    @PutMapping("/{username}/{quantity}/{id}")
    @PreAuthorize("#username == authentication.name")
    public ResponseEntity<Integer> addProduct(@PathVariable("username") String username, @PathVariable Integer quantity,
            @PathVariable Integer id) {
        LOG.info("PUT /shoppingcart/customer=" + username + "/productID=" + id + "/quantity=" + quantity);
        try {
            Product result = shoppingCartDao.addProduct(username, id, quantity);
            if (result != null) {
                return new ResponseEntity<>(HttpStatus.CREATED);
            } else {
                return new ResponseEntity<Integer>(id, HttpStatus.CONFLICT);
            }
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}