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

@Service
public class JwtService implements UserDetailsService{
    private UserDAO userDao;

    public JwtService(UserDAO userDAO) {
        this.userDao = userDAO;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //find username and 
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
    private Set<SimpleGrantedAuthority> getAuthorities(User user) {
        Set<SimpleGrantedAuthority>  authorities = new HashSet<SimpleGrantedAuthority>();
        user.getRole().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_"+role.getRoleName()));
        });
        return authorities;
    }
}
