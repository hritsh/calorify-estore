package com.estore.api.estoreapi;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import org.springframework.http.HttpHeaders;
import com.estore.api.estoreapi.jwtUtils.JwtUtil;
import com.estore.api.estoreapi.service.JwtService;

import io.jsonwebtoken.ExpiredJwtException;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
/**
 * Ensures that the specific filter related to extracting and verifying JWT token that was sent after successful user login is done only once
 * per request
 * 
 * {@link @Component} is a spring annotation that indicates that this class is a Component. Marks which classes are beans that are
 * Spring managed components
 * 
 * @author Team E
 */
@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private JwtService jwtService;
    /**
     * Extracts JWT token from Authorization header, checks if token was sent or expired
     * if not loads the user present in JWT token and their associated UserDetails
     * validates whether the token matches userDetails]
     * then matches Authentication Object into current Security Context to hold currently logged in user
     * 
     * @param {@linkplain HttpServletRequest request }  
     * @param {@linkplain HttpServletResponse response }
     * @param {@linkplain FilterChain filterChain }
     * 
     * @throws IOException
     * @throws ServletException
     * 
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, 
    FilterChain filterChain) throws ServletException, IOException {
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        String jwtToken = null;
        String username = null;
        if(header != null && header.startsWith("Bearer ")) {
            //length of bearer, to separate JWT token
            jwtToken = header.split(" ")[1].trim();
            try {
                username = jwtUtil.getUserNameFromJWT(jwtToken);
            } catch (IllegalArgumentException e) {
                System.out.println("Unable to get token");
            } catch (ExpiredJwtException e) {
                System.out.println("Token is expired");
            }
        } else {
            filterChain.doFilter(request, response);
            return;
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = jwtService.loadUserByUsername(username);

            if(jwtUtil.validateToken(jwtToken, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            } else {
                filterChain.doFilter(request, response);
                return;
            }

        }

        filterChain.doFilter(request, response);
    }
}
