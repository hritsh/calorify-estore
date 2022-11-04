package com.estore.api.estoreapi.persistence;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;
import java.io.File;
import java.io.IOException;

import com.estore.api.estoreapi.model.Role;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Implements the functionality for JSON file-based peristance for Roles
 * 
 * {@literal @}Component Spring annotation instantiates a single instance of
 * this
 * class and injects the instance into other classes as needed
 * 
 * @author Team-E
 */
@Component
public class RoleFileDAO implements RoleDAO{
    private static final Logger LOG = Logger.getLogger(InventoryFileDAO.class.getName());
    Map<Integer, Role> roles;
    private ObjectMapper objectMapper;

    private static int nextId;
    private String filename;
    /**
     * Creates a Role File Data Access Object
     * 
     * @param filename     Filename to read from and write to
     * @param objectMapper Provides JSON Object to/from Java Object serialization
     *                     and deserialization
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    public RoleFileDAO(@Value("${role.file}") String filename, ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();
    }
    /**
     * Generates the next id for a new {@linkplain Role role}
     * 
     * @return The next id
     */
    private synchronized static int nextId() {
        int id = nextId;
        ++nextId;
        return id;
    }
    /**
     * Generates an array of {@linkplain Product products} from the tree map
     * 
     * @return The array of {@link Product products}, may be empty
     */
    private Role[] getRolesArray() { 
        ArrayList<Role> roleArrayList = new ArrayList<>();

        for (Role role : roles.values()) {
            roleArrayList.add(role);
        }

        Role[] roleArray = new Role[roleArrayList.size()];
        roleArrayList.toArray(roleArray);
        return roleArray;
    }
    /**
     * Saves the {@linkplain Product products} from the map into the file as an
     * array of JSON objects
     * 
     * @return true if the {@link Product products} were written successfully
     * 
     * @throws IOException when file cannot be accessed or written to
     */
    private boolean save() throws IOException {
        Role[] roleArray = getRolesArray();

        objectMapper.writeValue(new File(filename), roleArray);
        return true;
    }
    /**
     * Loads {@linkplain Product products} from the JSON file into the map
     * <br>
     * Also sets next id to one more than the greatest id found in the file
     * 
     * @return true if the file was read successfully
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    private boolean load() throws IOException {
        roles = new TreeMap<>();
        nextId = 0;

        Role[] rolesArray = objectMapper.readValue(new File(filename),Role[].class);

        for (Role role : rolesArray) {
            roles.put(role.getRoleId(),role);
            if (role.getRoleId() > nextId)
                nextId = role.getRoleId();
        }
        ++nextId;
        return true;
    }
    /**
     ** {@inheritDoc}
     */
    @Override
    public Role getRole(int roleId) {
        synchronized (roles) {
            if (roles.containsKey(roleId))
                return roles.get(roleId);
            else
                return null;
        }
    }
    /**
     ** {@inheritDoc}
     */
    @Override
    public Role[] getRoles() {
        synchronized (roles) {
            return getRolesArray();
        }
    }
    /**
     ** {@inheritDoc}
     */
    @Override
    public Role createRole(Role role) throws IOException {
        synchronized (roles) {
            for (Role r : roles.values()) {
                if (r.getRoleName().equals(role.getRoleName().toLowerCase())) {
                    save();
                    return null;
                }
            }
            //role is made to be lowercase
            Role newRole = new Role(nextId(),  role.getRoleName().toLowerCase());
            roles.put(newRole.getRoleId(), newRole);
            save(); // may throw an IOException
            return newRole;
        }
    }
    /**
     ** {@inheritDoc}
     */
    @Override
    public boolean deleteRole(int roleId) throws IOException {
        synchronized (roles) {
            if (roles.containsKey(roleId)) {
                roles.remove(roleId);
                return save();
            } else
                return false;
        }
    }
}
