package com.estore.api.estoreapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import com.estore.api.estoreapi.model.Product;
import com.estore.api.estoreapi.model.Customer;
import com.estore.api.estoreapi.persistence.ShoppingCartDAO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Test the Shopping Cart Controller class
 * 
 * @author Team-E
 */
@Tag("Controller-tier")
public class ShoppingCartControllerTest {
    private ShoppingCartController cartController;
    private ShoppingCartDAO mockCartDAO;
    private Customer mockUser;
    private Product mockProduct;

    /**
     * Before each test, create a new inventoryController object and inject
     * a mock Product DAO
     */
    @BeforeEach
    public void setupinventoryController() {
        mockProduct = mock(Product.class);
        mockCartDAO = mock(ShoppingCartDAO.class);
        cartController = new ShoppingCartController(mockCartDAO);
        mockUser = mock(Customer.class);
        when(mockUser.getUsername()).thenReturn("mock");
        when(mockProduct.getId()).thenReturn(1);
        when(mockProduct.getQuantity()).thenReturn(5);
    }

    @Test
    public void testGetCartSuccess() throws IOException {
        // Setup
        Product[] test = { mockProduct };

        // Invoke
        when(mockCartDAO.getShoppingCart(mockUser.getUsername())).thenReturn(test);
        ResponseEntity<Product[]> response = cartController.getCart(mockUser.getUsername());

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(test, response.getBody());
    }

    @Test
    public void testGetCartFail() throws IOException {
        // Setup
        when(mockCartDAO.getShoppingCart(mockUser.getUsername())).thenReturn(null);

        // Invoke
        ResponseEntity<Product[]> response = cartController.getCart(mockUser.getUsername());

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testGetCartError() throws Exception {
        // Setup
        when(mockCartDAO.getShoppingCart(mockUser.getUsername())).thenThrow(new IOException());

        // Invoke
        ResponseEntity<Product[]> response = cartController.getCart(mockUser.getUsername());

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testDeleteProductSuccess() throws IOException {
        // Setup
        when(mockCartDAO.deleteProduct(mockUser.getUsername(), 1)).thenReturn(true);

        // Invoke
        ResponseEntity<Product> result = cartController.deleteProduct(mockUser.getUsername(), 1);

        // Analyze
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    public void testDeleteProductFail() throws IOException {
        // Setup
        when(mockCartDAO.deleteProduct(mockUser.getUsername(), 1)).thenReturn(false);

        // Invoke
        ResponseEntity<Product> result = cartController.deleteProduct(mockUser.getUsername(), 1);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    public void testDeleteProductError() throws IOException {
        // Setup
        when(mockCartDAO.deleteProduct(mockUser.getUsername(), 1)).thenThrow(new IOException());

        // Invoke
        ResponseEntity<Product> result = cartController.deleteProduct(mockUser.getUsername(), 1);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
    }

    @Test
    public void testClearCartSuccess() throws IOException {
        // Setup
        when(mockCartDAO.clearShoppingCart(mockUser.getUsername())).thenReturn(true);

        // Invoke
        ResponseEntity<Boolean> result = cartController.clearCart(mockUser.getUsername());

        // Analyze
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    public void testClearCartFail() throws IOException {
        // Setup
        when(mockCartDAO.clearShoppingCart(mockUser.getUsername())).thenReturn(false);

        // Invoke
        ResponseEntity<Boolean> result = cartController.clearCart(mockUser.getUsername());

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertFalse(result.getBody());
    }

    @Test
    public void testClearCartError() throws IOException {
        // Setup
        when(mockCartDAO.clearShoppingCart(mockUser.getUsername())).thenThrow(new IOException());

        // Invoke
        ResponseEntity<Boolean> result = cartController.clearCart(mockUser.getUsername());

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
    }

    @Test
    public void testAddProductSuccess() throws IOException {
        // Setup
        when(mockCartDAO.addProduct(mockUser.getUsername(), mockProduct.getId(), mockProduct.getQuantity())).thenReturn(mockProduct);

        // Invoke
        ResponseEntity<Integer> result = cartController.addProduct(mockUser.getUsername(), 5, mockProduct.getId());

        // Analyze
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
    }

    @Test
    public void testAddProductFail() throws IOException {
        // Setup
        when(mockCartDAO.addProduct(mockUser.getUsername(), mockProduct.getId(), mockProduct.getQuantity())).thenReturn(null);

        // Invoke
        ResponseEntity<Integer> result = cartController.addProduct(mockUser.getUsername(), mockProduct.getQuantity(), mockProduct.getId());

        // Analyze
        assertEquals(HttpStatus.CONFLICT, result.getStatusCode());
        assertEquals(mockProduct.getId(), result.getBody());
    }

    @Test
    public void testAddProductError() throws IOException {
        // Setup
        when(mockCartDAO.addProduct(mockUser.getUsername(), mockProduct.getId(), mockProduct.getQuantity())).thenThrow(new IOException());

        // Invoke
        ResponseEntity<Integer> result = cartController.addProduct(mockUser.getUsername(), 5, mockProduct.getId());

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
    }

    @Test
    public void testcheckoutSuccess() throws IOException {
        // Setup
        when(mockCartDAO.checkout(mockUser.getUsername())).thenReturn(true);

        // Invoke
        ResponseEntity<Boolean> result = cartController.checkout(mockUser.getUsername());

        // Analyze
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    public void testcheckoutFail() throws IOException {
        // Setup
        when(mockCartDAO.checkout(mockUser.getUsername())).thenReturn(false);

        // Invoke
        ResponseEntity<Boolean> result = cartController.checkout(mockUser.getUsername());

        // Analyze
        assertEquals(HttpStatus.CONFLICT, result.getStatusCode());
        assertFalse(result.getBody());
    }

    @Test
    public void testcheckoutError() throws IOException {
        // Setup
        when(mockCartDAO.checkout(mockUser.getUsername())).thenThrow(new IOException());

        // Invoke
        ResponseEntity<Boolean> result = cartController.checkout(mockUser.getUsername());

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
    }
}