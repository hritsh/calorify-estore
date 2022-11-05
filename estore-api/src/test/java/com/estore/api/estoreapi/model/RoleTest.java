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
        // Setup
        // Invoke
        Role role = new Role(1, "admin");
        role.setRoleId(1);
        role.setRoleName("admin");

        // Analyze
        assertEquals(1, role.getRoleId());
        assertEquals("admin", role.getRoleName());
    }

    @Test
    public void testName() {
        // Invoke
        Role role = new Role(1, "admin");
        // Analyze
        assertEquals("admin", role.getRoleName());
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
