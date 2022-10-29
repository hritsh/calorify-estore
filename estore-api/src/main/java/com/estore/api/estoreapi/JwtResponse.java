package com.estore.api.estoreapi;

import com.estore.api.estoreapi.model.User;

public class JwtResponse {
    private User user;
    private String jwtToken;

    public JwtResponse(User user, String jwtToken) {
        this.user = user;
        this.jwtToken = jwtToken; 
    }

    public User getUser() {
        return user;
    }
    public String getJwtToken() {
        return jwtToken;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }
}
