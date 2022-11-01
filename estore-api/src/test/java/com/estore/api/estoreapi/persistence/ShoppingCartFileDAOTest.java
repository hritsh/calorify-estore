package com.estore.api.estoreapi.persistence;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Arrays;

import com.estore.api.estoreapi.model.Product;
import com.estore.api.estoreapi.model.Customer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the Shopping Cart File DAO
 * 
 * @author Team-E
 */
@Tag("Persistence-tier")
public class ShoppingCartFileDAOTest {
    ShoppingCartFileDAO shoppingCartFileDAO;
    UserFileDAO mockUserFileDAO;
    InventoryDAO mockInventoryDAO;
    Customer customer;
    Customer mockCustomer;

    /**
     * Setup before each Test
     * Create a new shopping cart file DAO with a mock user file DAO passed in
     * Create a new customer with an empty shopping cart
     * 
     * @throws IOException
     */
    @BeforeEach
    public void setup() throws IOException {
        mockUserFileDAO = mock(UserFileDAO.class);
        mockInventoryDAO = mock(InventoryDAO.class);
        shoppingCartFileDAO = new ShoppingCartFileDAO(mockUserFileDAO, mockInventoryDAO);
        customer = new Customer("username");
        mockCustomer = mock(Customer.class);
        when(mockCustomer.getUsername()).thenReturn("mock");
        when(mockUserFileDAO.getUser(customer.getUsername())).thenReturn(customer);
    }

    @Test
    public void testaddProduct() throws IOException {

        // Setup
        Product testProduct = new Product(1, "Miso Pumpkin Salad", "miso.jpg", 200, 5);
        when(mockInventoryDAO.createClone(testProduct.getId(), testProduct.getQuantity())).thenReturn(testProduct);

        // Invoke
        shoppingCartFileDAO.addProduct(customer.getUsername(), testProduct.getId(), testProduct.getQuantity());

        // Analyze
        Product[] expected = { testProduct };
        Product[] actual = customer.getCart();
        assertTrue(Arrays.equals(expected, actual));
    }

    @Test
    public void testdeleteProduct() throws IOException {

        // Setup
        Product testProduct = new Product(1, "Miso Pumpkin Salad", "miso.jpg", 200, 5);
        customer.addProduct(testProduct);

        // Invoke
        shoppingCartFileDAO.deleteProduct(customer.getUsername(), 1);

        // Analyze
        Product[] expected = {};
        Product[] actual = customer.getCart();
        assertTrue(Arrays.equals(expected, actual));
    }

    @Test
    public void testgetShoppingCart() throws IOException {

        // Setup
        Product testProduct = new Product(1, "Miso Pumpkin Salad", "miso.jpg", 200, 5);
        Product testProductTwo = new Product(2, "Guacamole", "guacamole.jpg", 300, 5);
        customer.addProduct(testProduct);
        customer.addProduct(testProductTwo);

        // Invoke
        Product[] actual = shoppingCartFileDAO.getShoppingCart(customer.getUsername());

        // Analyze
        Product[] expected = { testProduct, testProductTwo };
        assertTrue(Arrays.equals(expected, actual));
    }

    @Test
    public void testclearShoppingCart() throws IOException {

        // Setup
        Product testProduct = new Product(1, "Miso Pumpkin Salad", "miso.jpg", 200, 5);
        Product testProductTwo = new Product(2, "Guacamole", "guacamole.jpg", 300, 5);
        customer.addProduct(testProduct);
        customer.addProduct(testProductTwo);

        // Invoke
        shoppingCartFileDAO.clearShoppingCart(customer.getUsername());

        // Analyze
        Product[] expected = {};
        Product[] actual = customer.getCart();
        assertTrue(Arrays.equals(expected, actual));
    }

    @Test
    public void testcheckout() throws IOException {

        // Setup
        Product testProduct = new Product(1, "Miso Pumpkin Salad", "miso.jpg", 200, 5);
        Product[] testCart = { testProduct };

        when(mockUserFileDAO.getUser(mockCustomer.getUsername())).thenReturn(mockCustomer);
        when(mockCustomer.getCart()).thenReturn(testCart);
        when(mockInventoryDAO.checkOut(testCart)).thenReturn(true);

        // Invoke
        boolean stat = shoppingCartFileDAO.checkout(mockCustomer.getUsername());

        // Analyze
        assertTrue((stat));
    }
}
