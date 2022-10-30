package com.estore.api.estoreapi.model;

import java.util.Set;
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
    static final String STRING_FORMAT = "User [username=%s, firstName=%s, lastName=%s, height=%d, weight=%d, age=%d, loggedIn=%b, isAdmin=%b]";
    public static final String ADMIN = "admin";

    //Testing with atleast 4 users
    @JsonProperty("username")
    private String username;
    @JsonProperty("password")
    private String password;
    @JsonProperty("role")
    //as user can have multiple roles
    private Set<Role> role;
    @JsonCreator
    public User(@JsonProperty("username") String username, @JsonProperty("password") String password, @JsonProperty("role") Set<Role> role) {
        this.username = username;
        this.password = password;
        this.role = role;
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
     * returns the string that represents the user password
     * 
     * @return a string representing this user's password
     */
    public String getPassword() {
        return this.password;
    }
    /**
     * Sets the password of the User
     * 
     * @param password The password of the User
     */
    public void setPassword(String password) {
        this.password = password;
    }
    /**
     * returns the boolean value of true or false whether current user account is admin
     * 
     * @return true or false value representing user account roles
     */
    public Set<Role> getRole() {
        return this.role;
    }
    /**
     * Sets the admin status of the User
     * 
     * @param isAdmin The value representing admin status of the User
     */
    public void setRole(Set<Role> role) {
        this.role = role;
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