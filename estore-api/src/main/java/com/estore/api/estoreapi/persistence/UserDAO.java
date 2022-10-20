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
    User getUser(int id) throws IOException;

    /**
     * Retrieves all {@linkplain User users}
     * 
     * @return An array of {@link User user} objects, may be empty
     * 
     * @throws IOException if an issue with underlying storage
     */
    User[] getUsers() throws IOException;
}
