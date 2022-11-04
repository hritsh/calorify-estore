package com.estore.api.estoreapi.persistence;

import java.io.IOException;
import com.estore.api.estoreapi.model.Product;

/**
 * Defines the interface for Product object persistence
 * 
 * @author Team-E
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

    /**
     * Finds all {@linkplain Product products} whose name contains the given text and whose calories is less than or equal given calories
     * It can use a combination of name and calories for advanced search query
     * 
     * @param containsText The text to match against
     * @param containsCalories The calories to match against
     * 
     * @return An array of {@link Product product} whose names contains the given
     *         text or have calories that less than or equal given calories, may be empty
     * 
     * @throws IOException if an issue with underlying storage
     */
    Product[] searchProduct(String containsText, Integer containsCalories) throws IOException;

    /**
     * Deletes a {@linkplain Product product} with the given id of a particular
     * product.
     * 
     * @param id The id of the {@link Product product}
     * 
     * @return true if the {@link Product product} was deleted
     * 
     *         false if product with the given id does not exist
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    boolean deleteProduct(int id) throws IOException;

    /**
     * Updates and saves a {@linkplain Product product}
     * 
     * @param {@link Product product} object to be updated and saved
     * 
     * @return updated {@link Product product} if successful, null if
     *         {@link Product product} could not be found
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    Product updateProduct(Product product) throws IOException;

    /**
     * Changes the ammount of the inventory has in stock
     * 
     * @param {@link Product passed} array of products to be checked out
     * 
     * @return false:
     *         if the quantity of the {@linkplain Product product} that is being
     *         checked out is larger than the quantity the inventory has in stock
     *         if a {@link Product product} that is being checked out no longer is
     *         available in the inventory
     *         true otherwise indicating a successful action
     * 
     * @throws IOException
     */
    Boolean checkOut(Product[] passed) throws IOException;

    /**
     * Creates a clone of an already existing {@linkplain Product product}
     * 
     * @param id       The id of the {@link Product product} to clone
     * @param quantity The new quantity to set the clone to
     * @return The newly created {@link Product product}
     */
    Product createClone(int id, int quantity);
}
