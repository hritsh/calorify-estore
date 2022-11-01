package com.estore.api.estoreapi.controller;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.io.IOException;
import java.util.logging.Logger;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.estore.api.estoreapi.JwtRequest;
import com.estore.api.estoreapi.JwtResponse;
import com.estore.api.estoreapi.jwtUtils.JwtUtil;
import com.estore.api.estoreapi.model.User;
import com.estore.api.estoreapi.persistence.UserDAO;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
/**
 * Handles the REST API requests for the authentication of users of given username and password and the generation of JWT Tokens
 * <p>
 * {@literal @}RestController Spring annotation identifies this class as a REST
 * API
 * method handler to the Spring framework
 * 
 * @author Team-E
 */
@RestController
@CrossOrigin
@RequestMapping(path = "auth")
public class LoginController {
    private UserDAO userDao;
    private JwtUtil jwtUtil; 
    private AuthenticationManager authenticationManager;
    private static final Logger LOG = Logger.getLogger(UserController.class.getName());
    
    /**
     * Creates a REST API controller to reponds to requests
     * 
     * @param userDao The {@link UserDAO User Data Access Object} to be used in login JWT Token generation mechanism
     *                     and return
     *                     <br>
     *                     This dependency is injected by the Spring Framework
     */
    public LoginController(UserDAO userDao, JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.userDao = userDao;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }
    /**
     * Responds to the POST request to authenticate user in system
     * 
     * @return ResponseEntity of {@link JwtResponse jwtResponse} which has user object returned along with newly generated token
     *         and header of AUTHORIZATION <JWT Token>
     *         HTTP status of OK<br>
     *         If credentials do not match users.json, then return ResponseEntity with HTTP status of UNAUTHORIZED
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PostMapping("login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest jwtRequest) {
        LOG.info("/auth/login for user:"+jwtRequest.getUsername());
        User user=null;
        UserDetails userDetails=null;

        HttpHeaders headers = new HttpHeaders();

        String username = jwtRequest.getUsername();
        String password = jwtRequest.getPassword();
        String newGeneratedToken = "";
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            user = userDao.getUser(username);
            if(user != null) {
                userDetails = new org.springframework.security.core.userdetails.User(username, password, getAuthorities(user));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            
            newGeneratedToken = jwtUtil.generateToken(userDetails);            
            headers.add(HttpHeaders.AUTHORIZATION, newGeneratedToken);
            JwtResponse response = new JwtResponse(user, newGeneratedToken);
            
            return new ResponseEntity<JwtResponse>(response, headers, HttpStatus.OK);
        } catch(BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch(DisabledException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
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
