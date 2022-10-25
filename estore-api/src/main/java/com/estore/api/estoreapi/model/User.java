package com.estore.api.estoreapi.model;

import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a User entity
 * 
 * @author Team-E
 */
public class User {
    private static final Logger LOG = Logger.getLogger(User.class.getName());

    // Package private for tests
    static final String STRING_FORMAT = "Product [name=%s, image=%s, calories=%d, price=%f]";
    public static final String ADMIN = "admin";

    // We intend to keep 30 products in the inventory.
    @JsonProperty("username")
    private String username;
    @JsonProperty("password")
    private String password;
    @JsonProperty("saltString")
    private String saltString;

    @JsonCreator
    public User(@JsonProperty("username") String username,
            @JsonProperty("password") String password, @JsonProperty("saltString") String saltString) {
        this.username = username;
        this.password = password;
        this.saltString = saltString;
    }

    /**
     * returns the string that represents the user, also known as the username
     * 
     * @return a string representing this user's username
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Sets the name of the User - necessary for JSON object to Java object
     * deserialization
     * 
     * @param username The username of the User
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the password of the User
     * 
     * @return The password of the User
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the User
     * 
     * @return The password of the User
     */
    public void setPassword(String password) {
        this.password = password;
    }

    public String getSaltString() {
        return saltString;
    }

    public void setSaltString(String saltString) {
        this.saltString = saltString;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }

        // check to see if the class type is equal
        if (object.getClass() == this.getClass() || object.getClass() == Customer.class) {
            User other = (User) object;

            // then check to see if the usernames are equal
            if (other.username.equals(this.username)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format(STRING_FORMAT, username);
    }
}
