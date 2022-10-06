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
     * Retrieves a {@linkplain Product product} with the given id
     * 
     * @param id The id of the {@link Product product} to get
     * 
     * @return a {@link Product product} object with the matching id
     *         <br>
     *         null if no {@link Product product} with a matching id is found
     * 
     * @throws IOException if an issue with underlying storage
     */
    Product getProduct(int id) throws IOException;

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
