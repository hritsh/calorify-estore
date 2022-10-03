package com.estore.api.estoreapi.persistence;

import java.io.IOException;
import com.estore.api.estoreapi.model.Product;

/**
 * Defines the interface for Product object persistence
 * 
 * @author SWEN Faculty
 */
public interface InventoryDAO {
    /**
     * Retrieves all {@linkplain Product products}
     * 
     * @return An array of {@link Product product} objects, may be empty
     * 
     * @throws IOException if an issue with underlying storage
     */
    Product[] getProducts() throws IOException;

    /**
     * Creates and saves a {@linkplain Product product}
     * 
     * @param product {@linkplain Product product} object to be created and saved
     *                <br>
     *                The id of the product object is ignored and a new uniqe id is
     *                assigned
     *
     * @return new {@link Product product} if successful, false otherwise
     * 
     * @throws IOException if an issue with underlying storage
     */
    Product createProduct(Product product) throws IOException;
}
