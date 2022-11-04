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
        String expected_name = "Miso Pumpkin Salad";
        String expected_image = "";
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
        String name = "Miso Pumpkin Salad";
        String image = "";
        int calories = 200;
        float price = 5;
        Product product = new Product(id, name, image, calories, price);

        String expected_name = "Salad";

        // Invoke
        product.setName(expected_name);

        // Analyze
        assertEquals(expected_name, product.getName());
    }

    @Test
    public void testToString() {
        // Setup
        int id = 1;
        String name = "Miso Pumpkin Salad";
        String image = "";
        int calories = 200;
        float price = 5;
        int quantity = 0;
        String expected_string = String.format(Product.STRING_FORMAT, id, name, image, calories, price, quantity);
        Product product = new Product(id, name, image, calories, price);

        // Invoke
        String actual_string = product.toString();

        // Analyze
        assertEquals(expected_string, actual_string);
    }
}
