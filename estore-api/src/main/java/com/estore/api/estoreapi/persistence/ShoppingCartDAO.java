package com.estore.api.estoreapi.persistence;

import java.io.IOException;

import com.estore.api.estoreapi.model.Product;
import com.estore.api.estoreapi.model.ShoppingCart;

/**
 * Defines the interface for Shopping Cart object persistence
 * 
 * @author Team-E
 */
public interface ShoppingCartDAO {
    ShoppingCart getShoppingCart(int id) throws IOException;

    int getTotalCalories(int id) throws IOException;

    float getTotalPrice(int id) throws IOException;

    boolean addProduct(int id, Product product, int quantity) throws IOException;

    boolean removeProduct(int id, Product product) throws IOException;

    boolean updateProductQuantity(int id, Product product, int quantity) throws IOException;

    boolean clearShoppingCart(int id) throws IOException;

    boolean checkoutShoppingCart(int id) throws IOException;

    boolean deleteShoppingCart(int id) throws IOException;
}
