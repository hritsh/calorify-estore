package com.estore.api.estoreapi.persistence;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.estore.api.estoreapi.model.Role;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.estore.api.estoreapi.model.Product;
import com.estore.api.estoreapi.model.Customer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the Shopping Cart File DAO
 * 
 * @author Team-E
 */
@Tag("Persistence-tier")
public class RoleFileDAOTest {
    RoleFileDAO roleFileDAO;
    Role[] testRoles;
    ObjectMapper mockObjectMapper;

    /**
     * Setup before each Test
     * Create a new shopping cart file DAO with a mock user file DAO passed in
     * Create a new customer with an empty shopping cart
     * 
     * @throws IOException
     */
    @BeforeEach
    public void setup() throws IOException {
        mockObjectMapper = mock(ObjectMapper.class);
        testRoles = new Role[1];
        testRoles[0] = new Role(1, "admin");
        testRoles[1] = new Role(2, "user");
        when(mockObjectMapper
                .readValue(new File("doesnt_matter.txt"), Role[].class))
                .thenReturn(testRoles);
        roleFileDAO = new RoleFileDAO("doesnt_matter.txt", mockObjectMapper);
    }
    @Test
    public void testSaveException() throws IOException{
        doThrow(new IOException())
            .when(mockObjectMapper)
                .writeValue(any(File.class),any(Role[].class));

        Role role = new Role(1, "admin");

        assertThrows(IOException.class,
                        () -> roleFileDAO.createRole(role),
                        "IOException not thrown");
    }
    @Test
    public void testConstructorException() throws IOException {
        // Setup
        ObjectMapper mockObjectMapper = mock(ObjectMapper.class);
        // We want to simulate with a Mock Object Mapper that an
        // exception was raised during JSON object deseerialization
        // into Java objects
        // When the Mock Object Mapper readValue method is called
        // from the inventoryFileDAO load method, an IOException is
        // raised
        doThrow(new IOException())
            .when(mockObjectMapper)
                .readValue(new File("doesnt_matter.txt"),Role[].class);

        // Invoke & Analyze
        assertThrows(IOException.class,
                        () -> new RoleFileDAO("doesnt_matter.txt",mockObjectMapper),
                        "IOException not thrown");
    }

    @Test
    public void testCreateRole() {
        // Setup
        Role role = new Role(1,"admin");

        // Invoke
        Role result = assertDoesNotThrow(() -> roleFileDAO.createRole(role),
                "Unexpected exception thrown");

        // Analyze
        assertNotNull(result);
        Role actual = roleFileDAO.getRole(role.getRoleId());
        assertEquals(actual.getRoleId(), role.getRoleId());
        assertEquals(actual.getRoleName(), role.getRoleName());
    }

    @Test
    public void testDeleteRoleNotFound() {
        // Invoke
        boolean result = assertDoesNotThrow(() -> roleFileDAO.deleteRole(1),
                "Unexpected exception thrown");

        // Analzye
        assertEquals(result, false);
        // We check the internal tree map size against the length
        assertEquals(roleFileDAO.roles.size(), testRoles.length);
    }

    @Test
    public void testDeleteRole() {
        // Invoke
        boolean result = assertDoesNotThrow(() -> roleFileDAO.deleteRole(1),
                "Unexpected exception thrown");

        // Analzye
        assertEquals(result, true);
        assertEquals(roleFileDAO.roles.size(), testRoles.length - 1);
    }
    @Test
    public void testGetRoles() {
        // Invoke
        Role[] roles = roleFileDAO.getRoles();

        // Analyze
        assertEquals(roles.length,testRoles.length);
        for (int i = 0; i < testRoles.length;++i)
            assertEquals(roles[i],testRoles[i]);
    }
    @Test
    public void testGetRole() throws IOException {
        // Invoke
        Role role = roleFileDAO.getRole(1);

        // Analzye
        assertEquals(role, testRoles[1]);
    }

    @Test
    public void testGetRoleNotFound() throws IOException {
        // Invoke
        Role role = roleFileDAO.getRole(1);

        // Analyze
        assertEquals(role, null);
    }
}
