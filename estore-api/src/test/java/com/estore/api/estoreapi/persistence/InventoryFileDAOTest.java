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
 * Test the Inventory File DAO class
 * 
 * @author Team-E
 */
@Tag("Persistence-tier")
public class InventoryFileDAOTest {
    InventoryFileDAO inventoryFileDAO;
    Product[] testProducts;
    ObjectMapper mockObjectMapper;

    /**
     * Before each test, we will create and inject a Mock Object Mapper to
     * isolate the tests from the underlying file
     * 
     * @throws IOException
     */
    @BeforeEach
    public void setupInventoryFileDAO() throws IOException {
        mockObjectMapper = mock(ObjectMapper.class);
        testProducts = new Product[3];
        testProducts[0] = new Product(1, "Miso Pumpkin Salad",
                "", 200,
                5);
        testProducts[1] = new Product(2, "Guacamole",
                "",
                100, 10);
        testProducts[2] = new Product(3, "Spinach Carbonara",
                "", 300,
                20);

        // When the object mapper is supposed to read from the file
        // the mock object mapper will return the product array above
        when(mockObjectMapper
                .readValue(new File("doesnt_matter.txt"), Product[].class))
                .thenReturn(testProducts);
        inventoryFileDAO = new InventoryFileDAO("doesnt_matter.txt", mockObjectMapper);
    }

    @Test
    public void testGetProduct() throws IOException {
        // Invoke
        Product product = inventoryFileDAO.getProduct(2);

        // Analzye
        assertEquals(product, testProducts[1]);
    }

    @Test
    public void testGetProductNotFound() throws IOException {
        // Invoke
        Product product = inventoryFileDAO.getProduct(98);

        // Analyze
        assertEquals(product, null);
    }

    @Test
    public void testCreateProduct() {
        // Setup
        Product product = new Product(4, "Fattoush Salad",
                "", 100, 10);

        // Invoke
        Product result = assertDoesNotThrow(() -> inventoryFileDAO.createProduct(product),
                "Unexpected exception thrown");
        Product result2 = assertDoesNotThrow(() -> inventoryFileDAO.createProduct(product),
                "Unexpected exception thrown");

        Product clone = inventoryFileDAO.createClone(4, 12);

        // Analyze
        assertNotNull(result);
        Product actual = inventoryFileDAO.getProduct(product.getId());
        assertEquals(actual.getId(), product.getId());
        assertEquals(actual.getName(), product.getName());
    }

    @Test
    public void testSearchProduct() throws IOException {
        // Invoke
        Product[] products = inventoryFileDAO.searchProduct("salad", null);
        Product[] products2 = inventoryFileDAO.searchProduct(null, 120);
        Product[] products3 = inventoryFileDAO.searchProduct("salad", 120);

        // Analyze
        assertEquals(products.length, 1);
        assertEquals(products2.length, 1);
        assertEquals(products3.length, 0);
        assertEquals(products[0], testProducts[0]);
    }

    @Test
    public void testSaveException() throws IOException {
        doThrow(new IOException())
                .when(mockObjectMapper)
                .writeValue(any(File.class), any(Product[].class));

        Product product = new Product(2, "Guacamole",
                "",
                100, 10);

        assertThrows(IOException.class,
                () -> inventoryFileDAO.createProduct(product),
                "IOException not thrown");
    }

    @Test
    public void testConstructorException() throws IOException {
        // Setup
        ObjectMapper mockObjectMapper = mock(ObjectMapper.class);
        // We want to simulate with a Mock Object Mapper that an
        // exception was raised during JSON object deseerialization
        // into Java objects
        // When the Mock Object Mapper readValue method is called
        // from the inventoryFileDAO load method, an IOException is
        // raised
        doThrow(new IOException())
                .when(mockObjectMapper)
                .readValue(new File("doesnt_matter.txt"), Product[].class);

        // Invoke & Analyze
        assertThrows(IOException.class,
                () -> new InventoryFileDAO("doesnt_matter.txt", mockObjectMapper),
                "IOException not thrown");
    }

    @Test
    public void testUpdateProduct() {
        // Setup
        Product product = new Product(1, "Apple",
                "https://clipart.world/wp-content/uploads/2020/06/red-apple-vector-1028143.jpg", 200, 5);

        // Invoke
        Product result = assertDoesNotThrow(() -> inventoryFileDAO.updateProduct(product),
                "Unexpected exception thrown");

        // Analyze
        assertNotNull(result);
        Product actual = inventoryFileDAO.getProduct(product.getId());
        assertEquals(actual, product);
    }

    @Test
    public void testUpdateProductNotFound() {
        // Setup
        Product product = new Product(99, "Orange",
                "https://clipart.world/wp-content/uploads/2020/06/red-apple-vector-1028143.jpg", 200, 5);

        // Invoke
        Product result = assertDoesNotThrow(() -> inventoryFileDAO.updateProduct(product),
                "Unexpected exception thrown");

        // Analyze
        assertNull(result);
    }

    @Test
    public void testDeleteProductNotFound() {
        // Invoke
        boolean result = assertDoesNotThrow(() -> inventoryFileDAO.deleteProduct(99),
                "Unexpected exception thrown");

        // Analzye
        assertEquals(result, false);
        // We check the internal tree map size against the length
        assertEquals(inventoryFileDAO.products.size(), testProducts.length);
    }

    @Test
    public void testDeleteProduct() {
        // Invoke
        boolean result = assertDoesNotThrow(() -> inventoryFileDAO.deleteProduct(2),
                "Unexpected exception thrown");

        // Analzye
        assertEquals(result, true);
        // We check the internal tree map size against the length
        // of the test products array - 1 (because of the delete)
        // Because products attribute of InventoryFileDAO is package private
        // we can access it directly
        assertEquals(inventoryFileDAO.products.size(), testProducts.length - 1);
    }

    @Test
    public void testGetProducts() {
        // Invoke
        Product[] products = inventoryFileDAO.getProducts();

        // Analyze
        assertEquals(products.length, testProducts.length);
        for (int i = 0; i < testProducts.length; ++i)
            assertEquals(products[i], testProducts[i]);
    }

    @Test
    public void testCheckOut() {
        // Setup
        Product product = new Product(4, "Fattoush Salad",
                "", 100, 10);

        // Invoke
        Product result = assertDoesNotThrow(() -> inventoryFileDAO.createProduct(product),
                "Unexpected exception thrown");
        Product[] products = inventoryFileDAO.getProducts();

        Boolean checkedOut = assertDoesNotThrow(() -> inventoryFileDAO.checkOut(products),
                "Unexpected exception thrown");

        // Analyze
        assertNotNull(checkedOut);
    }
}