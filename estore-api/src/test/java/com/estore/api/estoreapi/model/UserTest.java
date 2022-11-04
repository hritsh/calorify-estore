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
public class UserTest {
    @Test
    public void testCtor() {
        String expected_username = "christin";
        String expected_password = "christin";
        Set<Role> expected_roleSet = new HashSet<>();
        Role r = new Role(1, "admin");
        expected_roleSet.add(r);
        
        User user = new User(expected_username, expected_password, expected_roleSet);

        assertEquals(expected_username, user.getUsername());
        assertEquals(expected_password, user.getPassword());
        assertEquals(expected_roleSet, user.getRole());
    }
    @Test
    public void testName() {
        // Setup
        String username = "christin";
        String password = "christin";
        Set<Role> roleSet = new HashSet<>();
        Role r = new Role(1, "admin");
        roleSet.add(r);

        User user = new User(username, password, roleSet);

        String expected_name = "christin";

        // Invoke
        user.setUsername(expected_name);

        // Analyze
        assertEquals(expected_name, user.getUsername());
    }

    @Test
    public void testToString() {
        // Setup
        String username = "christin";
        String password = "christin";
        Set<Role> roleSet = new HashSet<>();
        Role r = new Role(1, "admin");
        roleSet.add(r);

        String expected_string = String.format(User.STRING_FORMAT, username);
        User user = new User(username, password, roleSet);

        // Invoke
        String actual_string = user.toString();

        // Analyze
        assertEquals(expected_string, actual_string);
    }
}
