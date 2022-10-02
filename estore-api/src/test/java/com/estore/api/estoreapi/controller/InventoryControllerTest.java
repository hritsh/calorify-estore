package com.estore.api.estoreapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
    public void testDeleteProduct() throws IOException { // deleteProduct may throw IOException
        // Setup
        int productId = 10;
        // when deleteInventory is called return true, simulating successful deletion
        when(mockInventoryDAO.deleteProduct(productId)).thenReturn(true);

        // Invoke
        ResponseEntity<Product> response = inventoryController.deleteProduct(productId);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
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
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }
 @Test
    public void testDeleteProductHandleException() throws IOException { // deleteProduct may throw IOException
        // Setup
        int productId = 10;
        // When deleteProduct is called on the Mock Product DAO, throw an IOException
        doThrow(new IOException()).when(mockInventoryDAO).deleteProduct(productId);

        // Invoke
        ResponseEntity<Product> response = inventoryController.deleteProduct(productId);
        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

}