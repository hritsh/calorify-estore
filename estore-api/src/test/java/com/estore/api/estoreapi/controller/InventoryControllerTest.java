package com.estore.api.estoreapi.controller;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.io.IOException;

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
 * @author 
 */
@Tag("Controller-tier")
public class InventoryControllerTest {
    private InventoryController inventoryController;
    private InventoryDAO mockInventoryDAO;

    /**
     * Before each test, create a new InventoryController object and inject
     * a mock Hero DAO
     */
    @BeforeEach
    public void setupHeroController() {
        mockInventoryDAO = mock(InventoryDAO.class);
        inventoryController = new InventoryController(mockInventoryDAO);
    }
    @Test
    public void testSearchProductByName() throws IOException { // searchProduct may throw IOException
        // Setup
        String searchString = "to";
        Product[] products = new Product[2];
        products[0] = new Product(1,"Galactic Potato","", 200, 10);
        products[1] = new Product(2,"Bombasto Pepper","",300,20);
        // When searchProduct is called with the search string, return the two product above
        when(mockInventoryDAO.searchProduct(searchString, null)).thenReturn(products);

        // Invoke
        ResponseEntity<Product[]> response = inventoryController.searchHeroes(searchString, null);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(products,response.getBody());
    }
    @Test
    public void testSearchProductsByPrice() throws IOException {
        Integer searchPrice = 10;
        Product[] products = new Product[3];
        products[0] = new Product(1, "Apple", "", 100, 5);
        products[1] = new Product(2,"Galactic Potato","", 200, 10);
        products[2] = new Product(3,"Bombasto Pepper","",300,20);

        Product[] splitArrayResult = Arrays.copyOfRange(products, 0, 2);

        // When searchProduct is called with the search price, return the two product that have price less than or equal to 10
        when(mockInventoryDAO.searchProduct(null, searchPrice)).thenReturn(splitArrayResult);

        // Invoke
        ResponseEntity<Product[]> response = inventoryController.searchHeroes(null, searchPrice);
       
        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(splitArrayResult,response.getBody()); 
    }
    @Test
    public void testSearchProductsByNameAndPrice() throws IOException {
        Integer searchPrice = 10;
        String searchString = "po";

        Product[] products = new Product[4];
        products[0] = new Product(1, "Apple", "", 100, 5);
        products[1] = new Product(2,"Pomogrenate", "", 50, 8);
        products[2] = new Product(3,"Galactic Potato","", 200, 10);
        products[3] = new Product(4,"Bombasto Pepper","",300,20);

        Product[] splitArrayResult = Arrays.copyOfRange(products, 1, 3);

        // When searchProduct is called with the search price, return the two product that have price less than or equal to 10 and contain the specified search string
        when(mockInventoryDAO.searchProduct(searchString, searchPrice)).thenReturn(splitArrayResult);

        // Invoke
        ResponseEntity<Product[]> response = inventoryController.searchHeroes(searchString, searchPrice);
       
        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(splitArrayResult,response.getBody()); 
    }
    @Test
    public void testSearchHeroesHandleException() throws IOException { // searchProduct may throw IOException
        // Setup
        String searchString = "an";
        // When searchProduct is called on the Mock Inventory DAO, throw an IOException
        doThrow(new IOException()).when(mockInventoryDAO).searchProduct(searchString, null);

        // Invoke
        ResponseEntity<Product[]> response = inventoryController.searchHeroes(searchString, null);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

}
