package com.estore.api.estoreapi.persistence;

import java.io.IOException;

import com.estore.api.estoreapi.model.Product;
import com.estore.api.estoreapi.model.ShoppingCart;
import com.estore.api.estoreapi.model.Customer;

/**
 * The interface that holds the abstracted methods for the
 * {@linkplain ShoppingCartFileDAO} class
 * 
 * @author Team-E
 */
public interface ShoppingCartDAO {

    /**
     * When given a {@linkplain Customer customer}'s username and the desired
     * {@link Product
     * product} indicated by id along with quantity 
     * to add to their {@link ShoppingCart shopping cart}, this method
     * calls upon the underlying method within the {@link Customer customer} class
     * to add
     * the {@link Product product} to their {@link ShoppingCart shopping cart}.
     * 
     * @param username the username of the {@link Customer customer} that is adding
     *                 a
     *                 {@link Product product}
     * @param id   the id of the {@link Product product} that is being added
     * @param quantity  the number of {@link Product product} to be added
     * 
     * @return the {@link Product product} that was added
     * 
     * @throws IOException If an issue occured whilst accessing the json files
     */
    Product addProduct(String username, int id, int quantity)
            throws IOException;

    /**
     * Deletes a {@linkplain Product product} within the specified {@link Customer
     * customer}'s {@link ShoppingCart shopping cart}
     * 
     * @param username the username of the {@link Customer customer} that is
     *                 deleting a
     *                 {@link Product product}
     * @param id       the id of the {@link Product product} to delete
     * 
     * @return a boolean indicating whether or not the deletion was successful
     * 
     * @throws IOException If an issue occured whilst accessing the json files
     */
    Boolean deleteProduct(String username, Integer id) throws IOException;

    /**
     * A method that gets the entire {@linkplain Customer customer}'s
     * {@link ShoppingCart
     * shopping cart} in the form of an
     * array of {@link Product products}
     * 
     * @param username the username of the {@link Customer customer} that is
     *                 invoking this method
     * 
     * @return the array of {@link Product products} in this specific
     *         {@link Customer customer}'s
     *         {@link ShoppingCart shopping cart}
     */
    Product[] getShoppingCart(String username) throws IOException;

    /**
     * Clears the specified {@linkplain Customer customer}'s {@link ShoppingCart
     * shopping
     * cart}
     * 
     * @param username the username of the {@link Customer customer} that wishes to
     *                 clear their
     *                 {@link ShoppingCart shopping cart}
     * 
     * @return a boolean indicating that the {@link ShoppingCart shopping cart} was
     *         cleared
     * 
     * @throws IOException If an issue occured whilst accessing the json files
     */
    boolean clearShoppingCart(String username) throws IOException;

    /**
     * Executes the action of checking out for a {@linkplain Customer customer}
     * The method checks to see if the checkout is allowable, and if so continue to
     * reduce the inventory's stock
     * based on the {@link Customer customer's} {@link ShoppingCart cart} and
     * proceed to clear the cart
     * 
     * @param username the username of the {@link Customer customer} that wishes to
     *                 checkout
     * 
     * @return a boolean indicating the success of the action
     * 
     * @throws IOException otherwise
     */
    Boolean checkout(String username) throws IOException;
}