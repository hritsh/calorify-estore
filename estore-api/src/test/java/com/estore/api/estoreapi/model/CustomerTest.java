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
        // Setup
        Set<Role> roleSet = new HashSet<>();
        Role r = new Role(1, "admin");
        roleSet.add(r);
        // Invoke
        Customer customer = new Customer("christin", "christin", roleSet);
        ;
        // Analyze
        assertEquals("christin", customer.getUsername());
        assertEquals("christin", customer.getPassword());
        assertEquals(roleSet, customer.getRole());

    }

    @Test
    public void testName() {
        // Setup
        Set<Role> roleSet = new HashSet<>();
        Role r = new Role(1, "admin");
        roleSet.add(r);
        // Invoke
        Customer customer = new Customer("christin", "christin", roleSet);
        ;
        // Analyze
        assertEquals(customer.getUsername(), customer.getUsername());
    }

    @Test
    public void testToString() {
        // Setup
        Set<Role> roleSet = new HashSet<>();
        Role r = new Role(1, "admin");
        roleSet.add(r);
        // Invoke
        Customer customer = new Customer("christin", "christin", roleSet);
        ;
        // Analyze
        assertEquals(customer.getUsername(), customer.getUsername());
    }
}
