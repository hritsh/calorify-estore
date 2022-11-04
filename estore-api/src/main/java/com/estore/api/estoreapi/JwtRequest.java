package com.estore.api.estoreapi;
/**
 * Represents a Jwt Request entity that will be used for login and authentication
 * 
 * @author Team-E
 */
public class JwtRequest {
    private String username;
    private String password;
    /**
     * Constructor for the request object representing a sent request for login
     * 
     * @param username username string sent by the user
     * @param password password string sent by the user
     */
    public JwtRequest(String username, String password) {
        this.username = username;
        this.password = password; 
    }
    public JwtRequest() {
    }

    /**
     * returns the string that represents the username that was sent in the user request
     * 
     * @return a string representing username sent
     */
    public String getUsername() {
        return username;
    }
    /**
     * Sets the username that was sent in JwtRequest object
     * 
     * @param username The new username
     */
    public void setUsername(String username) {
        this.username = username;
    }
    /**
     * returns the string that represents the password that was sent in the user request
     * 
     * @return a string representing password sent
     */
    public String getPassword() {
        return password;
    }
    /**
     * Sets the password that was sent in JwtRequest object
     * 
     * @param password The new password
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
