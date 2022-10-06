package com.estore.api.estoreapi.model;

import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a Product entity
 * 
 * @author Team-E
 */
public class Product {
    private static final Logger LOG = Logger.getLogger(Product.class.getName());

    // Package private for tests
    static final String STRING_FORMAT = "Product [id=%d, name=%s, image=%s, calories=%d, price=%f]";

// We intend to keep 30 products in the inventory.

    @JsonProperty("id")
    private int id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("image") 
    private String image;
    @JsonProperty("calories")
    private int calories;
    @JsonProperty("price")
    private float price;

    /**
     * Create a product with the given id and name
     * 
     * @param id       The id of the product
     * @param name     The name of the product
     * @param image    The image url of the product
     * @param calories The calories of the product
     * @param price    The price of the product
     * 
     *                 {@literal @}JsonProperty is used in serialization and
     *                 deserialization
     *                 of the JSON object to the Java object in mapping the
     *                 fields. If a field
     *                 is not provided in the JSON object, the Java field gets
     *                 the default Java
     *                 value, i.e. 0 for int
     */
    public Product(
            @JsonProperty("id") int id, @JsonProperty("name") String name, @JsonProperty("image") String image,
            @JsonProperty("calories") int calories, @JsonProperty("price") float price) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.calories = calories;
        this.price = price;
    }

    /**
     * Retrieves the id of the product
     * 
     * @return The id of the product
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the name of the product - necessary for JSON object to Java object
     * deserialization
     * 
     * @param name The name of the product
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retrieves the name of the product
     * 
     * @return The name of the product
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the image of the product - necessary for JSON object to Java object
     * deserialization
     * 
     * @param image The image of the product
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * Retrieves the image of the product
     * 
     * @return The image of the product
     */
    public String getImage() {
        return image;
    }

    /**
     * Sets the calories of the product - necessary for JSON object to Java object
     * deserialization
     * 
     * @param calories The calories of the product
     */
    public void setCalories(int calories) {
        this.calories = calories;
    }

    /**
     * Retrieves the calories of the product
     * 
     * @return The calories of the product
     */
    public int getCalories() {
        return calories;
    }

    /**
     * Sets the price of the product - necessary for JSON object to Java object
     * deserialization
     * 
     * @param price The price of the product
     */
    public void setPrice(float price) {
        this.price = price;
    }

    /**
     * Retrieves the price of the product
     * 
     * @return The price of the product
     */
    public float getPrice() {
        return price;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format(STRING_FORMAT, id, name, image, calories, price);
    }
}
