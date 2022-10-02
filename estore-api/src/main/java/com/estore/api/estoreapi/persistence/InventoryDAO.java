package com.estore.api.estoreapi.persistence;

import java.io.IOException;
import com.estore.api.estoreapi.model.Product;

public interface InventoryDAO {
  /**
     * Retrieves all {@linkplain Product product}
     * 
     * @return An array of {@link Product product} objects, may be empty
     * 
     * @throws IOException if an issue with underlying storage
     */
  Hero[] getProducts() throws IOException;
}
