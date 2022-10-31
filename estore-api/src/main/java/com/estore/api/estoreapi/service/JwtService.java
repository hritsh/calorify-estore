package com.estore.api.estoreapi.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.estore.api.estoreapi.model.User;
import com.estore.api.estoreapi.persistence.UserDAO;

/**
 * This file provides services related to JWT, it implements loadUserByUsername which returns a UserDetails object that is essential for
 * JWT token as it loads user specific data
 * 
 * {@Literal @Service} is a spring annotation that indicates that this class is in the Service layer, it is marked as a service provider
 * is to be instantiated
 * 
 * @author Team E
 */
@Service
public class JwtService implements UserDetailsService{
    private UserDAO userDao;

    public JwtService(UserDAO userDAO) {
        this.userDao = userDAO;
    }
    /**
     * Locates user by the username provided, then creates a new UserDetails object that is used to verify the password
     * and roles against the clients entered value
     * 
     * @param username Given username that will be searched in user file
     * 
     * @return a {@linkplain UserDetail userDetails } object indicating if the operation was successful
     * 
     * @throws UsernameNotFoundException when username given does not exist in users.json
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=null;
        UserDetails userDetails=null;
        try {
            user = userDao.getUser(username);
            
            if(user != null) {
                userDetails = new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthorities(user));
            } else {
                throw new UsernameNotFoundException("username is not valid");
            }
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        return userDetails;
    }
    /**
     * Local private methods to get set of authorities given a user which contains a set of roles inside its object
     * used by spring security while generating user details object which is essential for Token generation
     * 
     * @return Set of {@link SimpleGrantedAuthority authorities} of format ROLE_<roleName>
     */
    private Set<SimpleGrantedAuthority> getAuthorities(User user) {
        Set<SimpleGrantedAuthority>  authorities = new HashSet<SimpleGrantedAuthority>();
        user.getRole().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_"+role.getRoleName()));
        });
        return authorities;
    }
}
