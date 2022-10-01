package com.estore.api.estoreapi.persistence;

import java.io.IOException;
import com.estore.api.estoreapi.model.Product;

public interface InventoryDAO {
    /**
     * Finds all {@linkplain Product products} whose name contains the given text
     * 
     * @param containsText The text to match against
     * 
     * @return An array of {@link Product product} whose names contains the given text, may be empty
     * 
     * @throws IOException if an issue with underlying storage
     */
    Product[] searchProduct(String containsText, Integer containsPrice ) throws IOException;
}
