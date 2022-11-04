package com.estore.api.estoreapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
/**
 * The unit test suite for the User class
 * 
 * @author Team-E
 */
@Tag("Model-tier")
public class CustomerTest {
    @Test
    public void testCtor() {
        String expected_firstName = "chris";
        String expected_lastName = "Alex";
        String expected_gender = "M";
        int expected_height = 50;
        int expected_weight = 50;
        int expected_age = 30;
        Set<Role> roleSet = new HashSet<>();
        Role r = new Role(1, "admin");
        roleSet.add(r);
        String expected_roleName = "admin";

        Customer customer = new Customer("christin", "christin", roleSet);
        Customer customer = new Customer(expected_roleId, expected_roleName);

        assertEquals(expected_roleId, role.getRoleId());
        assertEquals(expected_roleName, role.getRoleName());
    }
    @Test
    public void testName() {
        // Setup
        int roleId = 1;
        String roleName = "admin";

        Customer customer = new Customer(roleId, roleName);

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
        Customer customer = new Customer(roleId, roleName);

        // Invoke
        String actual_string = role.toString();

        // Analyze
        assertEquals(expected_string, actual_string);
    }
}
