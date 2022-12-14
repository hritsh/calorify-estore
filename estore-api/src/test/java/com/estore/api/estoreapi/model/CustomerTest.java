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
        // Analyze
        assertEquals("Customer [firstName=null, lastName=null, gender=null, height=0, weight=0, age=0]",
                customer.toString());
    }

    @Test
    public void testSetDetails() {
        // Setup
        Set<Role> roleSet = new HashSet<>();
        Role r = new Role(1, "admin");
        roleSet.add(r);
        // Invoke
        Customer customer = new Customer("hritish", "hritish", roleSet);
        customer.setfirstName("Hritish");
        customer.setlastName("Balls");
        customer.setAge(300);
        customer.setGender("Male");
        customer.setHeight(180);
        customer.setWeight(2);
        customer.setSalad("2-0100010100-0010000-1010010-3-5-23-98");
        // Analyze
        assertEquals("Hritish", customer.getfirstName());
        assertEquals("Balls", customer.getlastName());
        assertEquals(300, customer.getAge());
        assertEquals("Male", customer.getGender());
        assertEquals(180, customer.getHeight());
        assertEquals(2, customer.getWeight());
        assertEquals("2-0100010100-0010000-1010010-3-5-23-98", customer.getSalad());
    }

}
