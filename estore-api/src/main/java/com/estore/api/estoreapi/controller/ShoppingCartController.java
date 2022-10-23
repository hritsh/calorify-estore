package com.estore.api.estoreapi.controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.estore.api.estoreapi.model.Product;
import com.estore.api.estoreapi.model.ShoppingCart;
import com.estore.api.estoreapi.persistence.ShoppingCartDAO;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Handles REST API requests for the ShoppingCart resource
 * (no longer used)
 */
@RestController
@RequestMapping("shoppingcart")
public class ShoppingCartController {
    private static final Logger LOG = Logger.getLogger(ShoppingCartController.class.getName());
    private ShoppingCartDAO shoppingCartDAO;

    public ShoppingCartController(ShoppingCartDAO shoppingCartDAO) {
        this.shoppingCartDAO = shoppingCartDAO;
    }

    /**
     * Responds to the GET request for a {@linkplain ShoppingCart shoppingCart} for
     * the given
     * user id
     * 
     * @param id The id used to locate the {@link User user} and get the users
     *           shoppingCart
     * 
     * @return ResponseEntity with {@link ShoppingCart shoppingCart} object and HTTP
     *         status of
     *         OK if found<br>
     *         ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("/{id}")
    public ResponseEntity<ShoppingCart> getShoppingCart(@PathVariable int id) {
        LOG.info("GET /shoppingCart/" + id);
        try {
            ShoppingCart shoppingCart = shoppingCartDAO.getShoppingCart(id);
            if (shoppingCart != null) {
                return new ResponseEntity<>(shoppingCart, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (IOException e) {
            LOG.log(Level.SEVERE, "Error getting shoppingCart", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Creates a {@linkplain Product product} with the provided product object
     * 
     * @param product - The {@link Product product} to create
     * 
     * @return ResponseEntity with created {@link Product product} object and HTTP
     *         status
     *         of CREATED<br>
     *         ResponseEntity with HTTP status of CONFLICT if {@link Product
     *         product}
     *         object already exists<br>
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PostMapping("")
    public ResponseEntity<Product> addToShoppingCart(@PathVariable int id, @RequestBody Product product,
            @PathVariable int quantity) {
        LOG.info("POST /shoppingcart " + id + product + quantity);
        // default quantity is 1 if not specified
        if (quantity == 0) {
            quantity = 1;
        }
        try {
            if (shoppingCartDAO.addProduct(id, product, quantity)) {
                return new ResponseEntity<>(product, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        } catch (IOException e) {
            LOG.log(Level.SEVERE, "Error adding product to shopping cart", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("")
    public ResponseEntity<Product> updateShoppingCart(@PathVariable int id, @RequestBody Product product,
            @PathVariable int quantity) {
        LOG.info("PUT /shoppingcart " + id + product + quantity);
        try {
            if (shoppingCartDAO.updateProductQuantity(id, product, quantity)) {
                return new ResponseEntity<>(product, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (IOException e) {
            LOG.log(Level.SEVERE, "Error updating product in shopping cart", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/remove/{id}")
    public ResponseEntity<Product> removeFromShoppingCart(@PathVariable int id, @RequestBody Product product) {
        LOG.info("PUT /shoppingcart/remove " + id + product);
        try {
            if (shoppingCartDAO.removeProduct(id, product)) {
                return new ResponseEntity<>(product, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (IOException e) {
            LOG.log(Level.SEVERE, "Error removing product from shopping cart", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/clear/{id}")
    public ResponseEntity<ShoppingCart> clearShoppingCart(@PathVariable int id) {
        LOG.info("PUT /shoppingcart/clear " + id);
        try {
            if (shoppingCartDAO.clearShoppingCart(id)) {
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (IOException e) {
            LOG.log(Level.SEVERE, "Error clearing shopping cart", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/checkout/{id}")
    public ResponseEntity<ShoppingCart> checkoutShoppingCart(@PathVariable int id) {
        LOG.info("PUT /shoppingcart/checkout " + id);
        try {
            if (shoppingCartDAO.checkoutShoppingCart(id)) {
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (IOException e) {
            LOG.log(Level.SEVERE, "Error checking out shopping cart", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Deletes the {@linkplain ShoppingCart shoppingCart} with the given id
     * 
     * @param id The id used to locate the {@link ShoppingCart shoppingCart} to
     *           delete
     * 
     * @return ResponseEntity with HTTP status of OK if deleted<br>
     *         ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ShoppingCart> deleteShoppingCart(@PathVariable int id) {
        LOG.info("DELETE /shoppingCart/" + id);
        try {
            if (shoppingCartDAO.deleteShoppingCart(id)) {
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (IOException e) {
            LOG.log(Level.SEVERE, "Error deleting shoppingCart", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}