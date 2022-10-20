package com.estore.api.estoreapi.model;

import java.util.HashMap;
import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a Shopping Cart entity
 * 
 * @author Team-E
 */
public class ShoppingCart {
    private static final Logger LOG = Logger.getLogger(ShoppingCart.class.getName());

    // Package private for tests
    static final String STRING_FORMAT = "ShoppingCart [userId=%s, items=%s, totalCalories=%d, totalPrice=%f, isOrderComplete=%b]";

    @JsonProperty("userId")
    private int userId;
    @JsonProperty("shoppingCart")
    private HashMap<Product, Integer> shoppingCart;
    @JsonProperty("totalCalories")
    private int totalCalories;
    @JsonProperty("totalPrice")
    private float totalPrice;
    @JsonProperty("isOrderComplete")
    private boolean isOrderComplete;

    /**
     * Create a product with the given id and name
     * 
     * @param userId          The userId of the product
     * @param shoppingCart    The map of products in the cart along with their
     *                        quantity
     * @param totalCalories   The total calories of the cart
     * @param totalPrice      The total price of the cart
     * @param isOrderComplete A flag indicating if the order is complete
     * 
     *                        {@literal @}JsonProperty is used in serialization and
     *                        deserialization
     *                        of the JSON object to the Java object in mapping the
     *                        fields. If a field
     *                        is not provided in the JSON object, the Java field
     *                        gets
     *                        the default Java
     *                        value, i.e. 0 for int
     */
    public ShoppingCart(
            @JsonProperty("userId") int userId, @JsonProperty("shoppingCart") HashMap<Product, Integer> shoppingCart,
            @JsonProperty("totalCalories") int totalCalories, @JsonProperty("totalPrice") float totalPrice) {
        this.userId = userId;
        this.shoppingCart = new HashMap<Product, Integer>();
        this.totalCalories = totalCalories;
        this.totalPrice = totalPrice;
    }

    /**
     * Retrieves the id of the product
     * 
     * @return The id of the product
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Retrieves the user id of the shopping cart
     * 
     * @param userId
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Retrieves the map of products in the shopping cart with their associated
     * quantities
     * 
     * @return
     */
    public HashMap<Product, Integer> getShoppingCart() {
        return shoppingCart;
    }

    /**
     * Sets the shopping cart map
     * 
     * @param shoppingCart
     */
    public void setShoppingCart(HashMap<Product, Integer> shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    /**
     * Sets the calories of the shopping cart - necessary for JSON object to Java
     * object
     * deserialization
     * 
     * @param totalCalories The calories of the product
     */
    public void setTotalCalories(int totalCalories) {
        this.totalCalories = totalCalories;
    }

    /**
     * Retrieves the total calories of the product
     * 
     * @return The total calories of the product
     */
    public int getTotalCalories() {
        return totalCalories;
    }

    /**
     * Sets the price of the product - necessary for JSON object to Java object
     * deserialization
     * 
     * @param price The price of the product
     */
    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    /**
     * Retrieves the price of the product
     * 
     * @return The price of the product
     */
    public float getTotalPrice() {
        return totalPrice;
    }

    /**
     * Retrieves the flag indicating if the order is complete
     * 
     * @return The flag indicating if the order is complete
     */
    public boolean isOrderComplete() {
        return isOrderComplete;
    }

    /**
     * Sets the flag indicating if the order is complete
     * 
     * @param isOrderComplete The flag indicating if the order is complete
     */
    public void setOrderComplete(boolean isOrderComplete) {
        this.isOrderComplete = isOrderComplete;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format(STRING_FORMAT, userId, shoppingCart.toString(), totalCalories, totalPrice,
                isOrderComplete);
    }
}
