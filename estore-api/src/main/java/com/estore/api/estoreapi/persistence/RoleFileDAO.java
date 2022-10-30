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

@Component
public class RoleFileDAO implements RoleDAO{
    private static final Logger LOG = Logger.getLogger(InventoryFileDAO.class.getName());
    Map<Integer, Role> roles;
    private ObjectMapper objectMapper; // Provides conversion between Product
                                       // objects and JSON text format written
                                       // to the file
    private static int nextId; // The next Id to assign to a new product
    private String filename;
    public RoleFileDAO(@Value("${role.file}") String filename, ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load(); // load the inventory from the file
    }
    private synchronized static int nextId() {
        int id = nextId;
        ++nextId;
        return id;
    }
    private Role[] getRolesArray() { 
        ArrayList<Role> roleArrayList = new ArrayList<>();

        for (Role role : roles.values()) {
            roleArrayList.add(role);
        }

        Role[] roleArray = new Role[roleArrayList.size()];
        roleArrayList.toArray(roleArray);
        return roleArray;
    }
    private boolean save() throws IOException {
        Role[] roleArray = getRolesArray();

        objectMapper.writeValue(new File(filename), roleArray);
        return true;
    }
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
    @Override
    /**
     ** {@inheritDoc}
     */
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
