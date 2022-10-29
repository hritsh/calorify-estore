package com.estore.api.estoreapi.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.io.IOException;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
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

@RestController
@CrossOrigin
@RequestMapping(path = "auth")
public class LoginController {
    private UserDAO userDao;
    private JwtUtil jwtUtil; 
    private AuthenticationManager authenticationManager;
    private static final Logger LOG = Logger.getLogger(UserController.class.getName());

    public LoginController(UserDAO userDao, JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.userDao = userDao;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest jwtRequest) {
        //incorporated JwtService previous methods createNewToken, authenticate here
        User user=null;
        UserDetails userDetails=null;

        HttpHeaders headers = new HttpHeaders();

        String username = jwtRequest.getUsername();
        String password = jwtRequest.getPassword();
        String newGeneratedToken = "";
        try {
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            user = userDao.getUser(username);
            if(user != null) {
                userDetails = new org.springframework.security.core.userdetails.User(username, password, getAuthorities(user));
            } else {
                throw new UsernameNotFoundException("username is not valid");
            }
            
            newGeneratedToken = jwtUtil.generateToken(userDetails);            
            headers.add(HttpHeaders.AUTHORIZATION, newGeneratedToken);
            JwtResponse response = new JwtResponse(user, newGeneratedToken);
            
            return new ResponseEntity<JwtResponse>(response, headers, HttpStatus.OK);
            
            //User user = (User) authenticate.getPrincipal();
        } catch(BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch(DisabledException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    private Set<SimpleGrantedAuthority> getAuthorities(User user) {
        Set<SimpleGrantedAuthority>  authorities = new HashSet<SimpleGrantedAuthority>();
        user.getRole().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_"+role.getRoleName()));
        });
        return authorities;
    }
}
