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
        testProducts[0] = new Product(1, "Apple",
                "https://clipart.world/wp-content/uploads/2020/06/red-apple-vector-1028143.jpg", 200,
                5);
        testProducts[1] = new Product(2, "Potato",
                "https://clipart.world/wp-content/uploads/2021/09/Potato-clipart-images.png",
                100, 10);
        testProducts[2] = new Product(3, "Bombasto Pepper",
                "https://clipart.world/wp-content/uploads/2022/08/Chili-Pepper-Clipart-Png.png", 300,
                20);

        // When the object mapper is supposed to read from the file
        // the mock object mapper will return the product array above
        when(mockObjectMapper
                .readValue(new File("doesnt_matter.txt"), Product[].class))
                .thenReturn(testProducts);
        inventoryFileDAO = new InventoryFileDAO("doesnt_matter.txt", mockObjectMapper);
    }

    @Test
    public void testCreateProduct() {
        // Setup
        Product product = new Product(4, "Banana",
                "https://clipart.world/wp-content/uploads/2020/07/three-bananas-2.jpg", 100, 10);

        // Invoke
        Product result = assertDoesNotThrow(() -> inventoryFileDAO.createProduct(product),
                "Unexpected exception thrown");

        // Analyze
        assertNotNull(result);
        Product actual = inventoryFileDAO.getProduct(product.getId());
        assertEquals(actual.getId(), product.getId());
        assertEquals(actual.getName(), product.getName());
    }

    @Test
    public void testSearchProduct() throws IOException {
        // Invoke
        Product[] products = inventoryFileDAO.searchProduct("po", null);

        // Analyze
        assertEquals(products.length, 2);
        assertEquals(products[0], testProducts[1]);
        assertEquals(products[1], testProducts[2]);
    }

    @Test
        public void testSaveException() throws IOException{
            doThrow(new IOException())
                .when(mockObjectMapper)
                    .writeValue(any(File.class),any(Product[].class));

        Product product = new Product(2, "Potato",
        "https://clipart.world/wp-content/uploads/2021/09/Potato-clipart-images.png",
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
                .readValue(new File("doesnt_matter.txt"),Product[].class);

        // Invoke & Analyze
        assertThrows(IOException.class,
                        () -> new InventoryFileDAO("doesnt_matter.txt",mockObjectMapper),
                        "IOException not thrown");
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
        // of the test heroes array - 1 (because of the delete)
        // Because heroes attribute of HeroFileDAO is package private
        // we can access it directly
        assertEquals(inventoryFileDAO.products.size(), testProducts.length - 1);
    }
}
