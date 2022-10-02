package com.estore.api.estoreapi.persistence;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.estore.api.estoreapi.model.Product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Test the Hero File DAO class
 * 
 * @author Team-E
 */
@Tag("Persistence-tier")
public class InventoryFileDAOTest {
    InventoryFileDAO inventoryFileDAO;
    Product[] testInventory;
    ObjectMapper mockObjectMapper;

    /**
     * Before each test, we will create and inject a Mock Object Mapper to
     * isolate the tests from the underlying file
     * @throws IOException
     */
    @BeforeEach
    public void setupInventoryFileDAO() throws IOException {
        mockObjectMapper = mock(ObjectMapper.class);
        testInventory = new Product[3];
        testInventory[0] = new Product(1, "Apple", "", 100, 5);
        testInventory[1] = new Product(2,"Pomogrenate", "", 50, 8);
        testInventory[2] = new Product(3,"Galactic Potato","", 200, 10);

        // When the object mapper is supposed to read from the file
        // the mock object mapper will return the product array above
        when(mockObjectMapper
            .readValue(new File("doesnt_matter.txt"),Product[].class))
                .thenReturn(testInventory);
        inventoryFileDAO = new InventoryFileDAO("doesnt_matter.txt",mockObjectMapper);
    }
    @Test
    public void testGetProduct() throws IOException {
        // Invoke
        Product product = inventoryFileDAO.getProduct(2);

        // Analzye
        assertEquals(product,testInventory[1]);
    }

  
    @Test
    public void testGetProductNotFound() throws IOException {
        // Invoke
        Product product = inventoryFileDAO.getProduct(98);

        // Analyze
        assertEquals(product,null);
    }
}

