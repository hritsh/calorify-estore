package com.estore.api.estoreapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.io.IOException;
import java.util.*;

import com.estore.api.estoreapi.persistence.InventoryDAO;
import com.estore.api.estoreapi.model.Product;

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
public class InventoryControllerTest {
    private InventoryController inventoryController;
    private InventoryDAO mockInventoryDAO;

    /**
     * Before each test, create a new InventoryController object and inject
     * a mock Inventory DAO
     */
    @BeforeEach
    public void setupInventoryController() {
        mockInventoryDAO = mock(InventoryDAO.class);
        inventoryController = new InventoryController(mockInventoryDAO);
    }

    @Test
    public void testGetProduct() throws IOException { // getProduct may throw IOException
        // Setup
        Product product = new Product(2, "Guacamole", "", 100, 10);
        // When the same id is passed in, our mock product DAO will return the product
        // object
        when(mockInventoryDAO.getProduct(product.getId())).thenReturn(product);

        // Invoke
        ResponseEntity<Product> response = inventoryController.getProduct(product.getId());

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(product, response.getBody());
    }

    @Test
    public void testGetProductNotFound() throws Exception { // getProduct may throw IOException
        // Setup
        int productId = 10;
        // When the same id is passed in, our mock Product DAO will return null,
        // simulating
        // no Product found
        when(mockInventoryDAO.getProduct(productId)).thenReturn(null);

        // Invoke
        ResponseEntity<Product> response = inventoryController.getProduct(productId);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testGetProductHandleException() throws Exception { // createProduct may throw IOException
        // Setup
        int userId = 99;
        // When getProduct is called on the Mock Product DAO, throw an IOException
        doThrow(new IOException()).when(mockInventoryDAO).getProduct(userId);

        // Invoke
        ResponseEntity<Product> response = inventoryController.getProduct(userId);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testSearchProductByName() throws IOException { // searchProduct may throw IOException
        // Setup
        String searchString = "to";
        Product[] products = new Product[2];
        products[0] = new Product(1,"Miso Pumpkin Salad","",200,5);
        products[1] = new Product(2, "Guacamole", "", 100, 10);
        // When searchProduct is called with the search string, return the two product
        // above
        when(mockInventoryDAO.searchProduct(searchString, null)).thenReturn(products);

        // Invoke
        ResponseEntity<Product[]> response = inventoryController.searchProducts(searchString, null);

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(products, response.getBody());
    }

    @Test
    public void testSearchProductsByCalories() throws IOException {
        Integer searchCalories = 10;
        Product[] products = new Product[3];
        products[0] = new Product(1, "Miso Pumpkin Salad", "", 200, 5);
        products[1] = new Product(2, "Guacamole", "", 100, 10);
        products[2] = new Product(3, "Spinach Carbonara", "", 300, 20);

        Product[] splitArrayResult = Arrays.copyOfRange(products, 0, 2);

        // When searchProduct is called with the search calories, return the two product
        // that have calories less than or equal to 10
        when(mockInventoryDAO.searchProduct(null, searchCalories)).thenReturn(splitArrayResult);

        // Invoke
        ResponseEntity<Product[]> response = inventoryController.searchProducts(null, searchCalories);

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(splitArrayResult, response.getBody());
    }

    @Test
    public void testSearchProductsByNameAndCalories() throws IOException {
        Integer searchCalories = 10;
        String searchString = "po";
        Product[] products = new Product[4];
        products[0] = new Product(1, "Miso Pumpkin Salad", "", 200, 5);
        products[1] = new Product(2, "Guacamole", "", 100, 10);
        products[2] = new Product(3, "Spinach Carbonara", "", 300, 20);
        products[3] = new Product(4, "Fattoush Salad", "", 300, 20);

        Product[] splitArrayResult = Arrays.copyOfRange(products, 1, 3);

        // When searchProduct is called with the search calories, return the two product
        // that have calories less than or equal to 10 and contain the specified search
        // string
        when(mockInventoryDAO.searchProduct(searchString, searchCalories)).thenReturn(splitArrayResult);

        // Invoke
        ResponseEntity<Product[]> response = inventoryController.searchProducts(searchString, searchCalories);

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(splitArrayResult, response.getBody());
    }

    @Test
    public void testSearchProductsHandleException() throws IOException { // searchProduct may throw IOException
        // Setup
        String searchString = "an";
        // When searchProduct is called on the Mock Inventory DAO, throw an IOException
        doThrow(new IOException()).when(mockInventoryDAO).searchProduct(searchString, null);

        // Invoke
        ResponseEntity<Product[]> response = inventoryController.searchProducts(searchString, null);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    /*****************************************************************
     * The following tests will fail until all InventoryController methods
     * are implemented.
     ****************************************************************/

    @Test
    public void testCreateProduct() throws IOException { // createProduct may throw IOException
        // Setup
        Product product = new Product(1, "Miso Pumpkin Salad",
                "", 200, 5);
        // when createProduct is called, return true simulating successful
        // creation and save
        when(mockInventoryDAO.createProduct(product)).thenReturn(product);

        // Invoke
        ResponseEntity<Product> response = inventoryController.createProduct(product);

        // Analyze
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(product, response.getBody());
    }

    @Test
    public void testCreateProductFailed() throws IOException { // createProduct may throw IOException
        // Setup
        Product product = new Product(1, "Miso Pumpkin Salad",
                "", 200, 5);
        // when createProduct is called, return false simulating failed
        // creation and save
        when(mockInventoryDAO.createProduct(product)).thenReturn(null);

        // Invoke
        ResponseEntity<Product> response = inventoryController.createProduct(product);

        // Analyze
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    public void testCreateProductHandleException() throws IOException { // createHProduct may throw IOException
        // Setup
        Product product = new Product(1, "Miso Pumpkin Salad",
                "",
                200, 5);

        // When createProduct is called on the Mock Inventory DAO, throw an IOException
        doThrow(new IOException()).when(mockInventoryDAO).createProduct(product);

        // Invoke
        ResponseEntity<Product> response = inventoryController.createProduct(product);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testDeleteProduct() throws IOException { // deleteProduct may throw IOException
        // Setup
        int productId = 10;
        // when deleteInventory is called return true, simulating successful deletion
        when(mockInventoryDAO.deleteProduct(productId)).thenReturn(true);

        // Invoke
        ResponseEntity<Product> response = inventoryController.deleteProduct(productId);

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testDeleteProductNotFound() throws IOException { // deleteProduct may throw IOException
        // Setup
        int productId = 10;
        // when deleteProduct is called return false, simulating failed deletion
        when(mockInventoryDAO.deleteProduct(productId)).thenReturn(false);
        // Invoke
        ResponseEntity<Product> response = inventoryController.deleteProduct(productId);
        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testDeleteProductHandleException() throws IOException { // deleteProduct may throw IOException
        // Setup
        int productId = 10;
        // When deleteProduct is called on the Mock Product DAO, throw an IOException
        doThrow(new IOException()).when(mockInventoryDAO).deleteProduct(productId);

        // Invoke
        ResponseEntity<Product> response = inventoryController.deleteProduct(productId);
    }
}