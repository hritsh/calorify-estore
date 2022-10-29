package com.estore.api.estoreapi.persistence;

import java.io.IOException;

import com.estore.api.estoreapi.model.Role;

public interface RoleDAO {
    Role getRole(int roleId) throws IOException;
    Role[] getRoles() throws IOException;
    Role createRole(Role role) throws IOException;
    boolean deleteRole(int roleId) throws IOException;
}
