package com.estore.api.estoreapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import com.estore.api.estoreapi.persistence.InventoryDAO;
import com.estore.api.estoreapi.model.Product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Test the Hero Controller class
 * 
 * @author Team-E
 */
@Tag("Controller-tier")
public class InventoryControllerTest {
    private InventoryController inventoryController;
    private InventoryDAO  mockInventoryDAO;

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
    public void testGetProduct() throws IOException {  // getProduct may throw IOException
        // Setup
        Product product = new Product(2,"Galactic Potato","", 200, 10);
        // When the same id is passed in, our mock product DAO will return the product object
        when(mockInventoryDAO.getProduct(product.getId())).thenReturn(product);

        // Invoke
        ResponseEntity<Product> response = inventoryController.getProduct(product.getId());

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(product,response.getBody());
    }
    @Test
    public void testGetProductNotFound() throws Exception { // getProduct may throw IOException
        // Setup
        int productId = 10;
        // When the same id is passed in, our mock Product DAO will return null, simulating
        // no Product found
        when(mockInventoryDAO.getProduct(productId)).thenReturn(null);

        // Invoke
        ResponseEntity<Product> response = inventoryController.getProduct(productId);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
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
}