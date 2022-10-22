package com.estore.api.estoreapi.persistence;

import java.io.IOException;
import com.estore.api.estoreapi.model.User;

public interface UserDAO {
    /**
     * Retrieves a {@linkplain User user} with the given id
     * 
     * @param id The id of the {@link User user} to get
     * 
     * @return a {@link User user} object with the matching id
     *         <br>
     *         null if no {@link User user} with a matching id is found
     * 
     * @throws IOException if an issue with underlying storage
     */
    User getUser(int id, String username, String password) throws IOException;
    /**
     * Retrieves all {@linkplain User users}
     * 
     * @return An array of {@link User user} objects, may be empty
     * 
     * @throws IOException if an issue with underlying storage
     */
    User[] getUsers() throws IOException;
    /**
     * Creates and saves a {@linkplain User user}
     * 
     * @param user {@linkplain User user} object to be created and saved
     * <br>
     * The id of the User object is ignored and a new uniqe id is assigned
     *
     * @return new {@link User user} if successful, false otherwise 
     * 
     * @throws IOException if an issue with underlying storage
     */
    User createUser(User user) throws IOException;
    /**
     * Updates and saves a {@linkplain User user} with selected details provided by user after authenticating
     * 
     * @param {@link User user} object to be updated and saved
     * 
     * @return updated {@link User user} if successful, null if
     * {@link User user} could not be found
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    User updateUserDetails(User user) throws IOException;
    /**
     * Updates and saves logout status of {@linkplain User user}
     * 
     * @param {@link int userId} object to be updated and saved
     * @param {@link boolean loggedInStatus}
     * 
     * @return updated {@link User user} login status if successful, null if
     * {@link User user} could not be found
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    User logoutUser(String username, String password, boolean loggedInStatus) throws IOException;
    /**
     * Deletes a {@linkplain User user} with the given id
     * 
     * @param id The id of the {@link User user}
     * @param password The password of the {@link User user} to authenticate deleting account
     * 
     * @return true if the {@link User user} was deleted
     * <br>
     * false if user with the given id does not exist
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    boolean deleteUser(int userId, String username, String password) throws IOException;
}
