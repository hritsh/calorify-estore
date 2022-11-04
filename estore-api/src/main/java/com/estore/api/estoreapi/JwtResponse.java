package com.estore.api.estoreapi;

import com.estore.api.estoreapi.model.User;

/**
 * Represents a Jwt Response entity that will be used for login and authentication
 * 
 * @author Team-E
 */
public class JwtResponse {
    private User user;
    private String jwtToken;
    /**
     * Constructor for the response object to be sent for a successful login request
     * 
     * @param user The user object related to the login request
     * @param jwtToken the token that was generated on successful login
     */
    public JwtResponse(User user, String jwtToken) {
        this.user = user;
        this.jwtToken = jwtToken; 
    }

    /**
     * returns the User object that will be sent as a response to a successful login request
     * 
     * @return User object
     */
    public User getUser() {
        return user;
    }
    /**
     * Sets the User object to be sent as a response
     * 
     * @param user The new user
     */
    public void setUser(User user) {
        this.user = user;
    }
    /**
     * returns the string representing the generated Jwt Token that will be sent as a response to a successful login request
     * 
     * @return a string representing jwt token sent
     */
    public String getJwtToken() {
        return jwtToken;
    }
    /**
     * Sets the jwt token to be sent as a response
     * 
     * @param jwtToken the jwt token
     */
    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }
}
