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
        // Setup
        Set<Role> roleSet = new HashSet<>();
        Set<Role> roleSet2 = new HashSet<>();
        Role r = new Role(1, "admin");
        Role r2 = new Role(2, "user");
        roleSet.add(r);
        roleSet2.add(r2);

        // Invoke
        User user = new User("christin", "christin", roleSet);
        User user2 = new User("christin", "christin", roleSet);
        User user3 = new User("basit", "basit", roleSet2);
        user2.setPassword("password");
        user2.setRole(roleSet);

        // Analyze
        assertEquals(user.equals(user), true);
        assertEquals(user.equals(user3), false);
        assertEquals("christin", user.getUsername());
        assertEquals("christin", user.getPassword());
        assertEquals(roleSet, user.getRole());

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
