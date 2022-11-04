package com.estore.api.estoreapi.persistence;

import java.io.IOException;
import com.estore.api.estoreapi.model.Role;

/**
 * Defines the interface for Role object persistence
 * 
 * @author Team-E
 */
public interface RoleDAO {
    /**
     * Retrieves a {@linkplain Role role} with the given id
     * 
     * @param roleId The id of the {@link Role role} to get
     * 
     * @return a {@link Role role} object with the matching id
     *         <br>
     *         null if no {@link Role role} with a matching id is found
     * 
     * @throws IOException if an issue with underlying storage
     */
    Role getRole(int roleId) throws IOException;
    /**
     * Retrieves all {@linkplain Role roles}
     * 
     * @return An array of {@link Role roles} objects, may be empty
     * 
     * @throws IOException if an issue with underlying storage
     */
    Role[] getRoles() throws IOException;
    /**
     * Creates and saves a {@linkplain Role role}
     * 
     * @param role {@linkplain Role role} object to be created and saved
     *                <br>
     *                The id of the role object is ignored and a new uniqe id is
     *                assigned
     *
     * @return new {@link Role role} if successful, false otherwise
     * 
     * @throws IOException if an issue with underlying storage
     */
    Role createRole(Role role) throws IOException;
    /**
     * Deletes a {@linkplain Role role} with the given id of a particular
     * role.
     * 
     * @param roleId The id of the {@link Role role}
     * 
     * @return true if the {@link Role role} was deleted
     * 
     *         false if role with the given id does not exist
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    boolean deleteRole(int roleId) throws IOException;
}
