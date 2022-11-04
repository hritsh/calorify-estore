package com.estore.api.estoreapi.model;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * The unit test suite for the Role class
 * 
 * @author Team-E
 */
@Tag("Model-tier")
public class RoleTest {
    @Test
    public void testCtor() {
        int expected_roleId = 1;
        String expected_roleName = "admin";
        
        Role role = new Role(expected_roleId, expected_roleName);

        assertEquals(expected_roleId, role.getRoleId());
        assertEquals(expected_roleName, role.getRoleName());
    }
    @Test
    public void testName() {
        // Setup
        int roleId = 1;
        String roleName = "admin";

        Role role = new Role(roleId, roleName);

        String expected_name = "admin";

        // Invoke
        role.setRoleName(expected_name);

        // Analyze
        assertEquals(expected_name, role.getRoleName());
    }

    @Test
    public void testToString() {
        // Setup
        int roleId = 1;
        String roleName = "admin";

        String expected_string = String.format(Role.STRING_FORMAT, roleId, roleName);
        Role role = new Role(roleId, roleName);

        // Invoke
        String actual_string = role.toString();

        // Analyze
        assertEquals(expected_string, actual_string);
    }
}
