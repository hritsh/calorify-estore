package com.estore.api.estoreapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * The unit test suite for the Product class
 * 
 * @author Team-E
 */
@Tag("Model-tier")
public class ProductTest {
    @Test
    public void testCtor() {
        // Setup
        int expected_id = 1;
        String expected_name = "Apple";
        String expected_image = "https://clipart.world/wp-content/uploads/2020/06/red-apple-vector-1028143.jpg";
        int expected_calories = 200;
        float expected_price = 5;

        // Invoke
        Product product = new Product(expected_id, expected_name, expected_image, expected_calories, expected_price);

        // Analyze
        assertEquals(expected_id, product.getId());
        assertEquals(expected_name, product.getName());
        assertEquals(expected_image, product.getImage());
        assertEquals(expected_calories, product.getCalories());
        assertEquals(expected_price, product.getPrice());
    }

    @Test
    public void testName() {
        // Setup
        int id = 1;
        String name = "Apple";
        String image = "https://clipart.world/wp-content/uploads/2020/06/red-apple-vector-1028143.jpg";
        int calories = 200;
        float price = 5;
        Product product = new Product(id, name, image, calories, price);

        String expected_name = "Red Apple";

        // Invoke
        product.setName(expected_name);

        // Analyze
        assertEquals(expected_name, product.getName());
    }

    @Test
    public void testToString() {
        // Setup
        int id = 1;
        String name = "Apple";
        String image = "https://clipart.world/wp-content/uploads/2020/06/red-apple-vector-1028143.jpg";
        int calories = 200;
        float price = 5;
        String expected_string = String.format(Product.STRING_FORMAT, id, name, image, calories, price);
        Product product = new Product(id, name, image, calories, price);

        // Invoke
        String actual_string = product.toString();

        // Analyze
        assertEquals(expected_string, actual_string);
    }
}
