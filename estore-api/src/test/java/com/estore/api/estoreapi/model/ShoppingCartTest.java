package com.estore.api.estoreapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.TreeMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the Shopping Cart model
 * 
 * @author Team-E
 */
@Tag("Model-Tier")
public class ShoppingCartTest {
    ShoppingCart testShoppingCart;
    ShoppingCart emptyShoppingCart;

    @BeforeEach
    public void setup() {
        TreeMap<Integer, Product> map = new TreeMap<>();
        Product expected = new Product(1, "Miso Pumpkin Salad", "miso.jpg", 200, 5);
        map.put(expected.getId(), expected);

        testShoppingCart = new ShoppingCart(map);
        emptyShoppingCart = new ShoppingCart(null);
    }

    @Test
    public void testAddProduct() {
        // Setup
        Product addedProduct = new Product(2, "Miso Pumpkin Salad", "miso.jpg", 200, 5);
        ShoppingCart cart = new ShoppingCart(null);

        // Invoke
        cart.addProduct(addedProduct);
        Product[] actual = cart.getItems();
        Product[] expected = { addedProduct };

        // Analysis
        assertTrue(Arrays.equals(expected, actual));
    }

    @Test
    public void testClearCart() {
        // invoke
        testShoppingCart.clearCart();
        int cartLength = testShoppingCart.getItems().length;
        // analyze
        assertEquals(0, cartLength);
    }

    @Test
    public void testRemoveProduct() {
        // Setup
        Product product = new Product(1, "Miso Pumpkin Salad", "miso.jpg", 200, 5);
        ShoppingCart actual = new ShoppingCart(null);
        actual.addProduct(product);

        // Invoke
        actual.removeProduct(0);
        int cartLength = actual.getItems().length;

        // Analysis
        assertEquals(0, cartLength);
    }
}
