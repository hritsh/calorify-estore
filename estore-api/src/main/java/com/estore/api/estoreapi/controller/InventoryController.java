package com.estore.api.estoreapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.estore.api.estoreapi.persistence.InventoryDAO;
import com.estore.api.estoreapi.model.Product;

/**
 * Handles the REST API requests for the Product resource
 * <p>
 * {@literal @}RestController Spring annotation identifies this class as a REST
 * API
 * method handler to the Spring framework
 * 
 * @author Team-E
 */

@RestController
@RequestMapping("products")
public class InventoryController {
    private static final Logger LOG = Logger.getLogger(InventoryController.class.getName());
    private InventoryDAO inventoryDao;

    /**
     * Creates a REST API controller to reponds to requests
     * 
     * @param inventoryDao The {@link InventoryDAO Inventory Data Access Object} to
     *                     perform CRUD operations
     *                     <br>
     *                     This dependency is injected by the Spring Framework
     */
    public InventoryController(InventoryDAO inventoryDao) {
        this.inventoryDao = inventoryDao;
    }

    /**
     * Responds to the GET request for a {@linkplain Product product} for the given
     * id
     * 
     * @param id The id used to locate the {@link Product product}
     * 
     * @return ResponseEntity with {@link Product product} object and HTTP status of
     *         OK if found<br>
     *         ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable int id) {
        LOG.info("GET /products/" + id);
        try {
            Product product = inventoryDao.getProduct(id);
            if (product != null)
                return new ResponseEntity<Product>(product, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the GET request for all {@linkplain Product products} whose name
     * contains
     * the text in name
     * 
     * Responds to the GET request for all {@linkplain Product products} whose price
     * is
     * less than or equal to query price
     * 
     * @param name  The name parameter which contains the text used to find the
     *              {@link Product products}
     * @param price The price parameter which contains the price used to find the
     *              {@link Product products}
     * 
     * @return ResponseEntity with array of {@link Product product} objects (may be
     *         empty) and
     *         HTTP status of OK<br>
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     *         <p>
     *         Example: Find all products that contain the text "ma"
     *         GET http://localhost:8080/products/?name=ma
     *         Example: Find all products whose price is less than or equal to 1
     *         GET http://localhost:8080/products/?price=1
     */
    @GetMapping("/")
    public ResponseEntity<Product[]> searchProducts(@RequestParam(required = false) String name,
            @RequestParam(required = false) Integer price) {
        try {
            if (name != null && price != null) {
                LOG.info("GET /products/?name=" + name + "&price=" + price);
                Product[] products = inventoryDao.searchProduct(name, price);
                if (products.length != 0)
                    return new ResponseEntity<Product[]>(products, HttpStatus.OK);
            } else if (name != null) {
                LOG.info("GET /products/?name=" + name);
                Product[] products = inventoryDao.searchProduct(name, null);
                if (products.length != 0)
                    return new ResponseEntity<Product[]>(products, HttpStatus.OK);
            } else if (price != null) {
                LOG.info("GET /products/?price=" + price);
                Product[] products = inventoryDao.searchProduct(null, price);
                if (products.length != 0)
                    return new ResponseEntity<Product[]>(products, HttpStatus.OK);
            } else {
                return new ResponseEntity<Product[]>(HttpStatus.NOT_FOUND);
            }
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return null;
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
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        LOG.info("POST /products " + product);

        // Replace below with your implementation
        try {
            Product[] products = inventoryDao.getProducts();
            // check if product already exists and return CONFLICT if it does
            for (Product p : products) {
                if (p.getName().equals(product.getName()))
                    return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
            // else, create product and return CREATED
            Product newProduct = inventoryDao.createProduct(product);
            return new ResponseEntity<Product>(newProduct, HttpStatus.CREATED);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Deletes a {@linkplain Product product} with the given id
     * 
     * @param id The id of the {@link Product product} to deleted
     * 
     * @return ResponseEntity HTTP status of OK if deleted<br>
     *         ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable int id) {
        LOG.info("DELETE /products/" + id);
        try {
            boolean product = inventoryDao.deleteProduct(id);
            if (product != false)
                return new ResponseEntity<>(HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}